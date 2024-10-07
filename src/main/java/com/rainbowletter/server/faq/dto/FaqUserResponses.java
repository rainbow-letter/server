package com.rainbowletter.server.faq.dto;

import com.rainbowletter.server.faq.domain.Faq;
import java.util.List;

public record FaqUserResponses(List<FaqUserResponse> faqs) {

	public static FaqUserResponses from(final List<Faq> faqs) {
		return new FaqUserResponses(
				faqs.stream()
						.map(FaqUserResponse::from)
						.toList()
		);
	}

}
