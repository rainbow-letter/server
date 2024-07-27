package com.rainbowletter.server.letter.infrastructure;

import static com.querydsl.core.types.dsl.Expressions.asNumber;
import static com.rainbowletter.server.letter.domain.QLetter.letter;
import static com.rainbowletter.server.pet.domain.QPet.pet;
import static com.rainbowletter.server.reply.domain.QReply.reply;
import static com.rainbowletter.server.user.domain.QUser.user;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rainbowletter.server.common.exception.RainbowLetterException;
import com.rainbowletter.server.letter.application.port.LetterRepository;
import com.rainbowletter.server.letter.domain.Letter;
import com.rainbowletter.server.letter.dto.LetterAdminPageRequest;
import com.rainbowletter.server.letter.dto.LetterAdminPageResponse;
import com.rainbowletter.server.letter.dto.LetterAdminRecentResponse;
import com.rainbowletter.server.letter.dto.LetterBoxResponse;
import com.rainbowletter.server.reply.domain.ReplyStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class LetterRepositoryImpl implements LetterRepository {

	private static final String LETTER_NOT_FOUND_MESSAGE = "편지를 찾을 수 없습니다.";

	private final JPAQueryFactory queryFactory;
	private final LetterJpaRepository letterJpaRepository;

	@Override
	public Letter findByIdAndUserIdOrElseThrow(final Long id, final Long userId) {
		return letterJpaRepository.findByIdAndUserId(id, userId)
				.orElseThrow(() -> new RainbowLetterException(
						LETTER_NOT_FOUND_MESSAGE,
						"id: [%d] userId: [%d]".formatted(id, userId)
				));
	}

	@Override
	public Letter findByIdOrElseThrow(final Long id) {
		return letterJpaRepository.findById(id)
				.orElseThrow(() -> new RainbowLetterException(LETTER_NOT_FOUND_MESSAGE, "id: [%d]".formatted(id)));
	}

	@Override
	public Letter findByEmailAndIdOrElseThrow(final String email, final Long id) {
		return Optional.ofNullable(
						queryFactory.selectFrom(letter)
								.join(user).on(letter.userId.eq(user.id))
								.where(user.email.eq(email).and(letter.id.eq(id)))
								.fetchOne()
				)
				.orElseThrow(() -> new RainbowLetterException(
						LETTER_NOT_FOUND_MESSAGE,
						"email: [%s] id: [%d]".formatted(email, id)
				));
	}

	@Override
	public Letter findByShareLinkOrElseThrow(final UUID shareLink) {
		return Optional.ofNullable(
						queryFactory.selectFrom(letter)
								.where(letter.shareLink.eq(shareLink))
								.fetchOne()
				)
				.orElseThrow(() -> new RainbowLetterException(
						LETTER_NOT_FOUND_MESSAGE,
						"share: [%s]".formatted(shareLink)
				));
	}

	@Override
	public List<Letter> findAllByPetId(final Long petId) {
		return letterJpaRepository.findAllByPetId(petId);
	}

	@Override
	public List<LetterBoxResponse> findAllLetterBoxByEmail(final String email) {
		final List<LetterBoxResponse> queryResults = queryFactory.select(Projections.constructor(
						LetterBoxResponse.class,
						letter.id,
						asNumber(0),
						letter.summary,
						letter.status,
						pet.name,
						reply.readStatus,
						letter.timeEntity.createdAt
				))
				.from(letter)
				.join(user).on(letter.userId.eq(user.id))
				.join(pet).on(letter.petId.eq(pet.id))
				.leftJoin(reply).on(letter.id.eq(reply.letterId))
				.where(user.email.eq(email))
				.orderBy(letter.timeEntity.createdAt.desc())
				.fetch();

		int sequence = queryResults.size();
		final List<LetterBoxResponse> letterBoxes = new ArrayList<>();
		for (LetterBoxResponse result : queryResults) {
			final LetterBoxResponse response = result.setNumber(sequence);
			letterBoxes.add(response);
			sequence--;
		}
		return letterBoxes;
	}

	@Override
	public List<LetterAdminRecentResponse> findAllRecentByPetId(final Long petId) {
		final List<LetterAdminRecentResponse> queryResults = queryFactory.select(Projections.constructor(
						LetterAdminRecentResponse.class,
						letter.id,
						letter.userId,
						letter.petId,
						asNumber(0),
						pet.name,
						letter.summary,
						letter.content,
						reply.inspection,
						reply.status,
						letter.timeEntity.createdAt,
						letter.timeEntity.updatedAt
				))
				.distinct()
				.from(letter)
				.join(pet).on(letter.petId.eq(pet.id))
				.leftJoin(reply).on(letter.id.eq(reply.letterId))
				.where(letter.petId.eq(petId))
				.limit(20)
				.orderBy(letter.timeEntity.createdAt.desc())
				.fetch();

		final Long letterCount = queryFactory.select(letter.count())
				.from(letter)
				.where(letter.petId.eq(petId))
				.fetchOne();

		assert letterCount != null;
		int sequence = Math.toIntExact(letterCount);
		final List<LetterAdminRecentResponse> recentResponses = new ArrayList<>();
		for (final LetterAdminRecentResponse result : queryResults) {
			final LetterAdminRecentResponse response = result.setNumber(sequence);
			recentResponses.add(response);
			sequence--;
		}
		return recentResponses;
	}

	@Override
	public Page<LetterAdminPageResponse> findAllByAdmin(final LetterAdminPageRequest request) {
		final JPQLQuery<Long> letterCountQuery = JPAExpressions.select(letter.count())
				.from(letter)
				.join(user).on(letter.userId.eq(user.id))
				.join(pet).on(letter.petId.eq(pet.id))
				.where(user.id.eq(pet.userId));
		final NumberExpression<Long> letterCount = asNumber(
				ExpressionUtils.as(letterCountQuery, Expressions.numberPath(Long.class, "count")));

		final List<LetterAdminPageResponse> result = queryFactory.select(Projections.constructor(
						LetterAdminPageResponse.class,
						letter.id,
						letter.userId,
						letter.petId,
						user.email,
						letterCount,
						letter.summary,
						letter.content,
						reply.inspection,
						reply.inspectionTime,
						reply.status,
						reply.submitTime,
						letter.timeEntity.createdAt,
						letter.timeEntity.updatedAt
				))
				.distinct()
				.from(letter)
				.join(user).on(letter.userId.eq(user.id))
				.leftJoin(reply).on(letter.id.eq(reply.letterId))
				.where(
						emailExpression(request.email()),
						inspectExpression(request.inspect()),
						statusExpression(request.status()),
						dateExpression(request.startDate(), request.endDate())
				)
				.offset(request.pageable().getOffset())
				.limit(request.pageable().getPageSize())
				.orderBy(letter.timeEntity.createdAt.desc())
				.fetch();

		final JPAQuery<Long> totalLetterPageQuery = queryFactory
				.select(letter.count())
				.from(letter)
				.join(user).on(letter.userId.eq(user.id))
				.leftJoin(reply).on(letter.id.eq(reply.letterId))
				.where(
						emailExpression(request.email()),
						inspectExpression(request.inspect()),
						statusExpression(request.status()),
						dateExpression(request.startDate(), request.endDate())
				);
		return PageableExecutionUtils.getPage(result, request.pageable(), totalLetterPageQuery::fetchOne);
	}

	private BooleanExpression emailExpression(final String email) {
		if (!StringUtils.hasText(email)) {
			return null;
		}
		return user.email.containsIgnoreCase(email);
	}

	private BooleanExpression inspectExpression(final Boolean inspect) {
		if (Objects.isNull(inspect)) {
			return null;
		}
		return reply.inspection.eq(inspect);
	}

	private BooleanExpression statusExpression(final ReplyStatus status) {
		if (Objects.isNull(status)) {
			return null;
		}
		return reply.status.eq(status);
	}

	private BooleanExpression dateExpression(final LocalDateTime startDate, final LocalDateTime endDate) {
		final BooleanExpression isGoeStartDate = letter.timeEntity.createdAt.goe(startDate);
		final BooleanExpression isLoeEndDate = letter.timeEntity.createdAt.loe(endDate);
		return Expressions.allOf(isGoeStartDate, isLoeEndDate);
	}

	@Override
	public Letter save(final Letter letter) {
		return letterJpaRepository.save(letter);
	}

	@Override
	public Long countByPetId(final Long petId) {
		return queryFactory.select(letter.count())
				.from(letter)
				.where(letter.petId.eq(petId))
				.fetchFirst();
	}

	@Override
	public void delete(final Letter letter) {
		letterJpaRepository.delete(letter);
	}

	@Override
	public void deleteAll(final List<Letter> letters) {
		final List<Long> ids = letters.stream()
				.map(Letter::getId)
				.toList();
		letterJpaRepository.deleteAllWithIds(ids);
	}

}
