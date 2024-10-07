package com.rainbowletter.server.letter.dto;

import com.rainbowletter.server.reply.domain.ReplyStatus;
import java.time.LocalDateTime;

public record LetterAdminPageResponse(
		Long id,
		Long userId,
		Long petId,
		Long replyId,
		String email,
		Long count,
		String summary,
		String content,
		boolean inspection,
		LocalDateTime inspectionTime,
		ReplyStatus status,
		LocalDateTime submitTime,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {

}
