package com.rainbowletter.server.letter.infrastructure;

import static com.rainbowletter.server.letter.domain.QLetter.letter;
import static com.rainbowletter.server.pet.domain.QPet.pet;
import static com.rainbowletter.server.reply.domain.QReply.reply;
import static com.rainbowletter.server.user.domain.QUser.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rainbowletter.server.common.exception.RainbowLetterException;
import com.rainbowletter.server.letter.application.port.LetterRepository;
import com.rainbowletter.server.letter.domain.Letter;
import com.rainbowletter.server.letter.dto.LetterBoxResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
		return queryFactory.select(Projections.constructor(
						LetterBoxResponse.class,
						letter.id,
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
