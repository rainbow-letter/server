package com.rainbowletter.server.temporary.dto;

import com.rainbowletter.server.common.validation.LetterContent;
import jakarta.validation.constraints.NotNull;

public record TemporaryUpdateRequest(
		@NotNull
		Long petId,

		@LetterContent
		String content
) {

	public TemporaryUpdate toDomainDto() {
		return new TemporaryUpdate(petId, content);
	}

}
