package com.rainbowletter.server.letter.dto;

import com.rainbowletter.server.common.validation.LetterContent;
import com.rainbowletter.server.common.validation.LetterSummary;
import jakarta.annotation.Nullable;

public record LetterCreateRequest(
		@LetterSummary
		String summary,

		@LetterContent
		String content,

		@Nullable
		String image
) {

	public LetterCreate toDomainDto() {
		return new LetterCreate(summary, content, image);
	}

}
