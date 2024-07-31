package com.rainbowletter.server.temporary.dto;

public record TemporaryExistsResponse(boolean exists) {

	public static TemporaryExistsResponse from(boolean exists) {
		return new TemporaryExistsResponse(exists);
	}

}
