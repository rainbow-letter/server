package com.rainbowletter.server.pet.dto;

import jakarta.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public record PetCreate(
		String name,
		String species,
		String owner,
		List<String> personalities,
		@Nullable LocalDate deathAnniversary,
		@Nullable String image
) {

	public static PetCreate of(
			final String name,
			final String species,
			final String owner,
			final List<String> personalities,
			final LocalDate deathAnniversary,
			final String image
	) {
		return new PetCreate(name, species, owner, personalities, deathAnniversary, image);
	}

}
