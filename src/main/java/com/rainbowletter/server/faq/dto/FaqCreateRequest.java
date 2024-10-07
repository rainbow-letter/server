package com.rainbowletter.server.faq.dto;

import static com.rainbowletter.server.common.validation.ValidationMessage.EMPTY_MESSAGE;

import com.rainbowletter.server.common.validation.FaqSummary;
import jakarta.validation.constraints.NotBlank;

public record FaqCreateRequest(
		@FaqSummary
		String summary,

		@NotBlank(message = EMPTY_MESSAGE)
		String detail
) {

	public FaqCreate toDomainDto() {
		return FaqCreate.of(summary, detail);
	}

}
