package com.rainbowletter.server.user.dto;

import com.rainbowletter.server.common.validation.UserEmail;
import com.rainbowletter.server.common.validation.UserPassword;

public record UserCreateRequest(
		@UserEmail
		String email,

		@UserPassword
		String password
) {

	public UserCreate toDomainDto() {
		return UserCreate.of(email, password);
	}

}
