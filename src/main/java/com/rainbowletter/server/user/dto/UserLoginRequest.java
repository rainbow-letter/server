package com.rainbowletter.server.user.dto;

import com.rainbowletter.server.common.validation.UserEmail;
import com.rainbowletter.server.common.validation.UserPassword;

public record UserLoginRequest(
		@UserEmail(message = LOGIN_MESSAGE)
		String email,

		@UserPassword(message = LOGIN_MESSAGE)
		String password
) {

	private static final String LOGIN_MESSAGE = "이메일 및 비밀번호를 확인 해주세요.";

	public UserLogin toDomainDto() {
		return UserLogin.of(email, password);
	}

}
