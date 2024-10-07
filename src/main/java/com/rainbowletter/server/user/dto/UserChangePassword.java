package com.rainbowletter.server.user.dto;

public record UserChangePassword(String password, String newPassword) {

	public static UserChangePassword of(final String password, final String newPassword) {
		return new UserChangePassword(password, newPassword);
	}

}
