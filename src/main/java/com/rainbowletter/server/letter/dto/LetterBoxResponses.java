package com.rainbowletter.server.letter.dto;

import java.util.List;

public record LetterBoxResponses(List<LetterBoxResponse> letters) {

	public static LetterBoxResponses from(List<LetterBoxResponse> letterBoxResponses) {
		return new LetterBoxResponses(letterBoxResponses);
	}

}
