package com.rainbowletter.server.letter.dto;

import com.rainbowletter.server.pet.dto.PetExcludeFavoriteResponse;
import com.rainbowletter.server.reply.dto.ReplyResponse;
import jakarta.annotation.Nullable;

public record LetterDetailResponse(
		PetExcludeFavoriteResponse pet,
		LetterResponse letter,
		ReplyResponse reply
) {

	public static LetterDetailResponse of(
			final PetExcludeFavoriteResponse petResponse,
			final LetterResponse letterResponse,
			@Nullable final ReplyResponse replyResponse
	) {
		return new LetterDetailResponse(petResponse, letterResponse, replyResponse);
	}

}
