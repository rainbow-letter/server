package com.rainbowletter.server.letter.dto;

import com.rainbowletter.server.pet.dto.PetExcludeFavoriteResponse;
import com.rainbowletter.server.reply.dto.ReplyResponse;

public record LetterDetailResponse(
		PetExcludeFavoriteResponse pet,
		LetterResponse letter,
		ReplyResponse reply
) {

	public static LetterDetailResponse of(
			final PetExcludeFavoriteResponse petResponse,
			final LetterResponse letterResponse,
			final ReplyResponse replyResponse
	) {
		return new LetterDetailResponse(petResponse, letterResponse, replyResponse);
	}

}
