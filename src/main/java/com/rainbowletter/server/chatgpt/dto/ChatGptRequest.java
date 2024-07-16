package com.rainbowletter.server.chatgpt.dto;

import java.util.List;

public record ChatGptRequest(
		String model,
		List<ChatGptPromptRequest> messages,
		Long max_tokens,
		Double temperature,
		Double top_p,
		Double frequency_penalty,
		Double presence_penalty,
		List<String> stop
) {

}
