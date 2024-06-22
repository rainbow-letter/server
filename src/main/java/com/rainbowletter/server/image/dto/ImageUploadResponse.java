package com.rainbowletter.server.image.dto;

public record ImageUploadResponse(String objectKey) {

	public static ImageUploadResponse from(String objectKey) {
		return new ImageUploadResponse(objectKey);
	}

}
