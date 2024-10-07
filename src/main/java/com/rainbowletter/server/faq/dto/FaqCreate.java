package com.rainbowletter.server.faq.dto;

public record FaqCreate(String summary, String detail) {

	public static FaqCreate of(final String summary, final String detail) {
		return new FaqCreate(summary, detail);
	}

}
