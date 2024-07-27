package com.rainbowletter.server.letter.dto;

import jakarta.annotation.Nullable;

public record LetterCreate(
		String summary,
		String content,
		@Nullable String image
) {

}
