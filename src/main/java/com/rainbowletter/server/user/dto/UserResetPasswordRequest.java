package com.rainbowletter.server.user.dto;

import com.rainbowletter.server.common.validation.UserPassword;

public record UserResetPasswordRequest(
		@UserPassword
		String newPassword
) {

	public UserResetPassword toDomainDto() {
		return UserResetPassword.from(newPassword);
	}

}
