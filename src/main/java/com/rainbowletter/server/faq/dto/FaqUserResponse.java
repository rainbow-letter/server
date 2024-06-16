package com.rainbowletter.server.faq.dto;

import com.rainbowletter.server.faq.domain.Faq;

public record FaqUserResponse(Long id, String summary, String detail) {

	public static FaqUserResponse from(final Faq faq) {
		return new FaqUserResponse(faq.getId(), faq.getSummary(), faq.getDetail());
	}

}
