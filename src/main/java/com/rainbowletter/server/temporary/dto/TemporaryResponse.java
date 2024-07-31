package com.rainbowletter.server.temporary.dto;

import com.rainbowletter.server.temporary.domain.Temporary;
import com.rainbowletter.server.temporary.domain.TemporaryStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record TemporaryResponse(
		Long id,
		Long userId,
		Long petId,
		UUID sessionId,
		String content,
		TemporaryStatus status,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {

	public static TemporaryResponse from(final Temporary temporary) {
		return new TemporaryResponse(
				temporary.getId(),
				temporary.getUserId(),
				temporary.getPetId(),
				temporary.getSessionId(),
				temporary.getContent(),
				temporary.getStatus(),
				temporary.getTimeEntity().getCreatedAt(),
				temporary.getTimeEntity().getUpdatedAt()
		);
	}

}
