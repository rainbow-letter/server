package com.rainbowletter.server.pet.dto;

import com.rainbowletter.server.pet.domain.Pet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record PetResponse(
		Long id,
		Long userId,
		String name,
		String species,
		String owner,
		List<String> personalities,
		LocalDate deathAnniversary,
		String image,
		FavoriteResponse favorite,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {

	public static PetResponse from(final Pet pet) {
		return new PetResponse(
				pet.getId(),
				pet.getUserId(),
				pet.getName(),
				pet.getSpecies(),
				pet.getOwner(),
				pet.getPersonalities(),
				pet.getDeathAnniversary(),
				pet.getImage(),
				FavoriteResponse.from(pet.getFavorite()),
				pet.getTimeEntity().getCreatedAt(),
				pet.getTimeEntity().getUpdatedAt()
		);
	}

}
