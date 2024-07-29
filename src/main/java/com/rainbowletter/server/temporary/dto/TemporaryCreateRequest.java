package com.rainbowletter.server.temporary.dto;

import com.rainbowletter.server.common.validation.LetterContent;
import jakarta.validation.constraints.NotNull;

public record TemporaryCreateRequest(
		@NotNull
		Long petId,

		@LetterContent
		String content
) {

	public TemporaryCreate toDomainDto() {
		return new TemporaryCreate(petId, content);
	}

}
