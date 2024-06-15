package com.rainbowletter.server.notification.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AlimTalkSendResponse(int result_code, String message) {

}
