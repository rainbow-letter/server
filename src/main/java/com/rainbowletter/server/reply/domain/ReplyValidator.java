package com.rainbowletter.server.reply.domain;

import com.rainbowletter.server.common.exception.RainbowLetterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyValidator {

	public void validateInspect(final Reply reply) {
		if (reply.isSubmitted()) {
			throw new RainbowLetterException("이미 발송 완료된 답장의 검수 여부는 변경할 수 없습니다.", reply.getId());
		}
	}

	public void validateSubmit(final Reply reply) {
		if (!reply.isInspection()) {
			throw new RainbowLetterException("답장을 발송하려면 검수가 완료되어야 합니다.", reply.getId());
		}
		if (reply.isSubmitted()) {
			throw new RainbowLetterException("이미 발송 완료된 답장입니다.", reply.getId());
		}
	}

	public void validateRead(final Reply reply) {
		if (!reply.isSubmitted()) {
			throw new RainbowLetterException("발송이 완료되지 않은 답장은 읽음 처리를 할 수 없습니다.", reply.getId());
		}
	}

}
