package com.rainbowletter.server.user.dto;

import com.rainbowletter.server.common.validation.UserPassword;

public record UserChangePasswordRequest(
		@UserPassword
		String password,

		@UserPassword
		String newPassword
) {

	public UserChangePassword toDomainDto() {
		return UserChangePassword.of(password, newPassword);
	}

}
