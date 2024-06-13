package com.rainbowletter.server.user.dto;

public record UserChangePhoneNumber(String phoneNumber) {

	public static UserChangePhoneNumber from(final String phoneNumber) {
		return new UserChangePhoneNumber(phoneNumber);
	}

}
