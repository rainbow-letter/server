package com.rainbowletter.server.reply.dto;

import com.rainbowletter.server.chatgpt.domain.ChatGptPromptType;
import com.rainbowletter.server.reply.domain.Reply;
import com.rainbowletter.server.reply.domain.ReplyReadStatus;
import com.rainbowletter.server.reply.domain.ReplyStatus;
import java.time.LocalDateTime;

public record ReplyResponse(
		Long id,
		Long petId,
		Long letterId,
		String summary,
		String content,
		ChatGptPromptType promptType,
		boolean inspection,
		LocalDateTime inspectionTime,
		ReplyStatus status,
		LocalDateTime submitTime,
		ReplyReadStatus readStatus,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {

	public static ReplyResponse from(final Reply reply) {
		return new ReplyResponse(
				reply.getId(),
				reply.getPetId(),
				reply.getLetterId(),
				reply.getSummary(),
				reply.getContent(),
				reply.getPromptType(),
				reply.isInspection(),
				reply.getInspectionTime(),
				reply.getStatus(),
				reply.getSubmitTime(),
				reply.getReadStatus(),
				reply.getTimeEntity().getCreatedAt(),
				reply.getTimeEntity().getUpdatedAt()
		);
	}

}
