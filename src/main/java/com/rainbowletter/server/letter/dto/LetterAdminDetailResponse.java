package com.rainbowletter.server.letter.dto;

import com.rainbowletter.server.pet.dto.PetExcludeFavoriteResponse;
import com.rainbowletter.server.reply.dto.ReplyResponse;
import com.rainbowletter.server.user.dto.UserInformationResponse;
import java.util.List;

public record LetterAdminDetailResponse(
		UserInformationResponse user,
		PetExcludeFavoriteResponse pet,
		LetterResponse letter,
		ReplyResponse reply,
		List<LetterAdminRecentResponse> recent
) {

	public static LetterAdminDetailResponse of(
			final UserInformationResponse userResponse,
			final PetExcludeFavoriteResponse petResponse,
			final LetterResponse letterResponse,
			final ReplyResponse replyResponse,
			final List<LetterAdminRecentResponse> recentResponses
	) {
		return new LetterAdminDetailResponse(userResponse, petResponse, letterResponse, replyResponse, recentResponses);
	}

}
