package com.rainbowletter.server.letter.dto;

import com.rainbowletter.server.letter.domain.Letter;
import com.rainbowletter.server.letter.domain.LetterStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record LetterResponse(
		Long id,
		Long userId,
		Long petId,
		int number,
		String summary,
		String content,
		UUID shareLink,
		String image,
		LetterStatus status,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {

	public static LetterResponse from(final Letter letter) {
		return new LetterResponse(
				letter.getId(),
				letter.getUserId(),
				letter.getPetId(),
				letter.getNumber(),
				letter.getSummary(),
				letter.getContent(),
				letter.getShareLink(),
				letter.getImage(),
				letter.getStatus(),
				letter.getTimeEntity().getCreatedAt(),
				letter.getTimeEntity().getUpdatedAt()
		);
	}

}
