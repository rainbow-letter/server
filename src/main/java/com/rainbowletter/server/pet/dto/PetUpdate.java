package com.rainbowletter.server.pet.dto;

import jakarta.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public record PetUpdate(
		String name,
		String species,
		String owner,
		List<String> personalities,
		@Nullable LocalDate deathAnniversary,
		@Nullable String image
) {

	public static PetUpdate of(
			final String name,
			final String species,
			final String owner,
			final List<String> personalities,
			final LocalDate deathAnniversary,
			final String image
	) {
		return new PetUpdate(name, species, owner, personalities, deathAnniversary, image);
	}

}
