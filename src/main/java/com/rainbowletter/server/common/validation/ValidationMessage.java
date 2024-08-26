package com.rainbowletter.server.common.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationMessage {

	public static final String NOT_NULL_MESSAGE = "NULL 값은 허용하지 않습니다.";
	public static final String EMPTY_MESSAGE = "항목을 입력해주세요.";
	public static final String PAST_OR_PRESENT_MESSAGE = "날짜가 과거가 아니거나, 형식이 잘못되었습니다.";

}
