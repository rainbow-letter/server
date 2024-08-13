package com.rainbowletter.server.user.infrastructure;

import static com.rainbowletter.server.user.domain.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rainbowletter.server.common.exception.RainbowLetterException;
import com.rainbowletter.server.user.application.port.UserRepository;
import com.rainbowletter.server.user.domain.User;
import com.rainbowletter.server.user.domain.UserStatus;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final JPAQueryFactory queryFactory;
	private final UserJpaRepository userJpaRepository;

	@Override
	public User save(final User user) {
		return userJpaRepository.save(user);
	}

	@Override
	public boolean existsByEmail(final String email) {
		return userJpaRepository.existsByEmail(email);
	}

	@Override
	public boolean existsLeaveByEmail(final String email) {
		return Objects.nonNull(
				queryFactory.selectOne()
						.from(user)
						.where(
								user.email.eq(email),
								user.status.eq(UserStatus.LEAVE)
						)
						.fetchFirst()
		);
	}

	@Override
	public boolean existsByPhoneNumber(final String phoneNumber) {
		return userJpaRepository.existsByPhoneNumber(phoneNumber);
	}

	@Override
	public User findByIdOrElseThrow(final Long id) {
		return userJpaRepository.findById(id)
				.orElseThrow(() -> new RainbowLetterException("사용자 고유 아이디를 찾을 수 없습니다.", id));
	}

	@Override
	public User findByEmailOrElseThrow(final String email) {
		return userJpaRepository.findByEmail(email)
				.orElseThrow(() -> new RainbowLetterException("사용자 이메일을 찾을 수 없습니다.", email));
	}

}
