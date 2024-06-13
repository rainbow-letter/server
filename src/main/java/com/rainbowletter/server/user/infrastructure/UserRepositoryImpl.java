package com.rainbowletter.server.user.infrastructure;

import com.rainbowletter.server.common.exception.RainbowLetterException;
import com.rainbowletter.server.user.application.port.UserRepository;
import com.rainbowletter.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

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
	public boolean existsByPhoneNumber(final String phoneNumber) {
		return userJpaRepository.existsByPhoneNumber(phoneNumber);
	}

	@Override
	public User findByEmailOrElseThrow(final String email) {
		return userJpaRepository.findByEmail(email)
				.orElseThrow(() -> new RainbowLetterException("사용자 이메일을 찾을 수 없습니다.", email));
	}

}
