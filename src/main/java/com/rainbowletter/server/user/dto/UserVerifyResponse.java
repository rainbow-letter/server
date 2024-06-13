package com.rainbowletter.server.user.dto;

public record UserVerifyResponse(String email, String role) {

	public static UserVerifyResponse of(String email, String role) {
		return new UserVerifyResponse(email, role);
	}

}
