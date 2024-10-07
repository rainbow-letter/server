package com.rainbowletter.server.chatgpt.dto;

public record ChatGptUsageResponse(int prompt_tokens, int completion_tokens, int total_tokens) {

}
