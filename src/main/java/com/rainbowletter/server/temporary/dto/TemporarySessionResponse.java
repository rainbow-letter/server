package com.rainbowletter.server.temporary.dto;

import com.rainbowletter.server.temporary.domain.Temporary;
import java.util.UUID;

public record TemporarySessionResponse(UUID sessionId) {

	public static TemporarySessionResponse from(final Temporary temporary) {
		return new TemporarySessionResponse(temporary.getSessionId());
	}

}
