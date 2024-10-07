package com.rainbowletter.server.letter.dto;

import com.rainbowletter.server.reply.domain.ReplyStatus;
import java.time.LocalDateTime;

public record LetterAdminRecentResponse(
		Long id,
		Long userId,
		Long petId,
		int number,
		String petName,
		String summary,
		String content,
		boolean inspection,
		ReplyStatus status,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {

}
