package com.rainbowletter.server.temporary.infrastructure;

import static com.rainbowletter.server.temporary.domain.QTemporary.temporary;
import static com.rainbowletter.server.user.domain.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rainbowletter.server.common.exception.RainbowLetterException;
import com.rainbowletter.server.temporary.application.port.TemporaryRepository;
import com.rainbowletter.server.temporary.domain.Temporary;
import com.rainbowletter.server.temporary.domain.TemporaryStatus;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TemporaryRepositoryImpl implements TemporaryRepository {

	private static final String TEMPORARY_NOT_FOUND_MESSAGE = "편지를 찾을 수 없습니다.";

	private final JPAQueryFactory queryFactory;
	private final TemporaryJpaRepository temporaryJpaRepository;

	@Override
	public Temporary findByIdAndEmailOrElseThrow(final Long id, final String email) {
		return Optional.ofNullable(
				queryFactory.selectFrom(temporary)
						.join(user).on(temporary.userId.eq(user.id))
						.where(
								user.email.eq(email),
								temporary.id.eq(id),
								temporary.status.eq(TemporaryStatus.SAVE)
						)
						.fetchOne()
		).orElseThrow(() -> new RainbowLetterException(TEMPORARY_NOT_FOUND_MESSAGE));
	}

	@Override
	public Temporary findByEmailAndPetIdOrElseThrow(final String email, final Long petId) {
		return Optional.ofNullable(
				queryFactory.selectFrom(temporary)
						.join(user).on(temporary.userId.eq(user.id))
						.where(
								user.email.eq(email),
								temporary.petId.eq(petId),
								temporary.status.eq(TemporaryStatus.SAVE)
						)
						.fetchOne()
		).orElseThrow(() -> new RainbowLetterException(TEMPORARY_NOT_FOUND_MESSAGE));
	}

	@Override
	public boolean existsByEmailAndPetId(final String email, final Long petId) {
		return Objects.nonNull(
				queryFactory.selectOne()
						.from(temporary)
						.join(user).on(temporary.userId.eq(user.id))
						.where(
								user.email.eq(email),
								temporary.petId.eq(petId),
								temporary.status.eq(TemporaryStatus.SAVE)
						)
						.fetchFirst()
		);
	}

	@Override
	public Temporary save(final Temporary temporary) {
		return temporaryJpaRepository.save(temporary);
	}

}
