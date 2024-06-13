package com.rainbowletter.server.user.dto;

public record UserResetPassword(String newPassword) {

	public static UserResetPassword from(final String newPassword) {
		return new UserResetPassword(newPassword);
	}

}
