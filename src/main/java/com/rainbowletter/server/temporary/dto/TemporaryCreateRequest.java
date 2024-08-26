package com.rainbowletter.server.temporary.dto;

import static com.rainbowletter.server.common.validation.ValidationMessage.NOT_NULL_MESSAGE;

import com.rainbowletter.server.common.validation.TemporaryContent;
import jakarta.validation.constraints.NotNull;

public record TemporaryCreateRequest(
		@NotNull(message = NOT_NULL_MESSAGE)
		Long petId,

		@TemporaryContent
		String content
) {

	public TemporaryCreate toDomainDto() {
		return new TemporaryCreate(petId, content);
	}

}
