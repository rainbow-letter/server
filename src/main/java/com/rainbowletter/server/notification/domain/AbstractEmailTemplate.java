package com.rainbowletter.server.notification.domain;

import com.rainbowletter.server.common.exception.RainbowLetterException;

public abstract class AbstractEmailTemplate {

	protected void validate(final EmailTemplateType type, final int length, final Object... args) {
		if (args.length != length) {
			throw new RainbowLetterException("이메일 템플릿 필수 파라미터 %d개가 필요합니다. 현재 %d개".formatted(length, args.length), type);
		}
	}

}
