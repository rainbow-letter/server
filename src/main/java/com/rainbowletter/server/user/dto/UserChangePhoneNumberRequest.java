package com.rainbowletter.server.user.dto;

import com.rainbowletter.server.common.validation.UserPhoneNumber;

public record UserChangePhoneNumberRequest(
		@UserPhoneNumber
		String phoneNumber
) {

	public UserChangePhoneNumber toDomainDto() {
		return UserChangePhoneNumber.from(phoneNumber);
	}

}
