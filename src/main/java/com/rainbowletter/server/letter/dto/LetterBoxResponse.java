package com.rainbowletter.server.letter.dto;

import com.rainbowletter.server.letter.domain.LetterStatus;
import com.rainbowletter.server.reply.domain.ReplyReadStatus;
import java.time.LocalDateTime;

public record LetterBoxResponse(
		Long id,
		int number,
		String summary,
		LetterStatus status,
		String petName,
		ReplyReadStatus readStatus,
		LocalDateTime createdAt
) {

}
