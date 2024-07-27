package com.rainbowletter.server.chatgpt.dto;

public record ChatGptChoiceResponse(ChatGptPromptRequest message, int index, String finish_reason) {

}
