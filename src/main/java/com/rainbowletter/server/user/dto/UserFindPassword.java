package com.rainbowletter.server.user.dto;

public record UserFindPassword(String email) {

	public static UserFindPassword from(final String email) {
		return new UserFindPassword(email);
	}

}
