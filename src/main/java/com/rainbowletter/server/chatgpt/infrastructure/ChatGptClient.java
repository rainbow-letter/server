package com.rainbowletter.server.chatgpt.infrastructure;

import com.rainbowletter.server.chatgpt.dto.ChatGptRequest;
import com.rainbowletter.server.chatgpt.dto.ChatGptResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface ChatGptClient {

	@PostExchange("/v1/chat/completions")
	ChatGptResponse execute(@RequestBody ChatGptRequest request);

}
