package com.rainbowletter.server.chatgpt.dto;

import java.util.List;

public record ChatGptResponse(
		String id,
		String object,
		int created,
		String model,
		List<ChatGptChoiceResponse> choices,
		ChatGptUsageResponse usage
) {

}
