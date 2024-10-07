package com.rainbowletter.server.user.dto;

import com.rainbowletter.server.common.validation.UserEmail;

public record UserFindPasswordRequest(@UserEmail String email) {

	public UserFindPassword toDomainDto() {
		return UserFindPassword.from(email);
	}

}
