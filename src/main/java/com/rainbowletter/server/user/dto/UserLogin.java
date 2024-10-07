package com.rainbowletter.server.user.dto;

public record UserLogin(String email, String password) {

	public static UserLogin of(final String email, final String password) {
		return new UserLogin(email, password);
	}

}
