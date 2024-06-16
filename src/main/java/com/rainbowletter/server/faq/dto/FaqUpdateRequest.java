package com.rainbowletter.server.faq.dto;

import static com.rainbowletter.server.common.validation.ValidationMessage.EMPTY_MESSAGE;
import static com.rainbowletter.server.common.validation.ValidationMessage.FAQ_SUMMARY_LENGTH_MESSAGE;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FaqUpdateRequest(
		@NotBlank(message = EMPTY_MESSAGE)
		@Size(max = 30, message = FAQ_SUMMARY_LENGTH_MESSAGE)
		String summary,

		@NotBlank(message = EMPTY_MESSAGE)
		String detail
) {

	public FaqUpdate toDomainDto() {
		return FaqUpdate.of(summary, detail);
	}

}
