package com.rainbowletter.server.faq.dto;

import com.rainbowletter.server.faq.domain.Faq;
import java.time.LocalDateTime;

public record FaqAdminResponse(
		Long id,
		String summary,
		String detail,
		boolean visibility,
		Long sequence,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {

	public static FaqAdminResponse from(final Faq faq) {
		return new FaqAdminResponse(
				faq.getId(),
				faq.getSummary(),
				faq.getDetail(),
				faq.isVisibility(),
				faq.getSequence(),
				faq.getTimeEntity().getCreatedAt(),
				faq.getTimeEntity().getUpdatedAt()
		);
	}

}
