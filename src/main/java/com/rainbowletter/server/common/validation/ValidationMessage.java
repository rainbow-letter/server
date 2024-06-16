package com.rainbowletter.server.common.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationMessage {

	public static final String EMPTY_MESSAGE = "항목을 입력해주세요.";
	public static final String FAQ_SUMMARY_LENGTH_MESSAGE = "제목은 30자 이하로 입력해 주세요.";

}
