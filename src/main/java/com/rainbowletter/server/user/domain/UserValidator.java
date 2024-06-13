package com.rainbowletter.server.user.domain;

import com.rainbowletter.server.common.exception.RainbowLetterException;
import com.rainbowletter.server.user.application.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

	private final UserRepository userRepository;

	public void validateEmail(final String email) {
		if (userRepository.existsByEmail(email)) {
			throw new RainbowLetterException("이미 존재하는 이메일입니다.", email);
		}
	}

	public void validatePhoneNumber(final String phoneNumber) {
		if (userRepository.existsByPhoneNumber(phoneNumber)) {
			throw new RainbowLetterException("이미 등록되어 있는 휴대폰 번호입니다.", phoneNumber);
		}
	}

}
