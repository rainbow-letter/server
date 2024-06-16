package com.rainbowletter.server.faq.dto;

import com.rainbowletter.server.faq.domain.Faq;
import java.util.List;

public record FaqAdminResponses(List<FaqAdminResponse> faqs) {

	public static FaqAdminResponses from(final List<Faq> faqs) {
		return new FaqAdminResponses(
				faqs.stream()
						.map(FaqAdminResponse::from)
						.toList()
		);
	}

}
