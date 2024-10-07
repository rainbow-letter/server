package com.rainbowletter.server.faq.dto;

public record FaqUpdate(String summary, String detail) {

	public static FaqUpdate of(final String summary, final String detail) {
		return new FaqUpdate(summary, detail);
	}

}
