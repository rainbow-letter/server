package com.rainbowletter.server.temporary.dto;

import com.rainbowletter.server.temporary.domain.Temporary;
import java.util.UUID;

public record TemporaryCreateResponse(Long id, UUID sessionId) {

	public static TemporaryCreateResponse from(final Temporary temporary) {
		return new TemporaryCreateResponse(temporary.getId(), temporary.getSessionId());
	}

}
