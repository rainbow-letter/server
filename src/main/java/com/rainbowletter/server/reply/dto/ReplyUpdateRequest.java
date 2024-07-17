package com.rainbowletter.server.reply.dto;

import com.rainbowletter.server.common.validation.LetterContent;
import com.rainbowletter.server.common.validation.LetterSummary;

public record ReplyUpdateRequest(
		@LetterSummary
		String summary,

		@LetterContent
		String content
) {

	public ReplyUpdate toDomainDto() {
		return ReplyUpdate.of(summary, content);
	}

}
