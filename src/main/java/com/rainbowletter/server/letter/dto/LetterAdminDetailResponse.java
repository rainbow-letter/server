package com.rainbowletter.server.letter.dto;

import com.rainbowletter.server.pet.dto.PetExcludeFavoriteResponse;
import com.rainbowletter.server.reply.dto.ReplyResponse;
import com.rainbowletter.server.user.dto.UserInformationResponse;
import java.util.List;

public record LetterAdminDetailResponse(
		LetterAdminDetailUserInformationResponse user,
		PetExcludeFavoriteResponse pet,
		LetterResponse letter,
		ReplyResponse reply,
		List<LetterAdminRecentResponse> recent
) {

	public static LetterAdminDetailResponse of(
			final UserInformationResponse userInformation,
			final Long letterCount,
			final PetExcludeFavoriteResponse petResponse,
			final LetterResponse letterResponse,
			final ReplyResponse replyResponse,
			final List<LetterAdminRecentResponse> recentResponses
	) {
		final var userResponse = LetterAdminDetailUserInformationResponse.from(userInformation, letterCount);
		return new LetterAdminDetailResponse(userResponse, petResponse, letterResponse, replyResponse, recentResponses);
	}

}
