package com.rainbowletter.server.pet.dto;

import java.time.LocalDate;

public record PetDashboardResponse(
		Long id,
		String name,
		Long letterCount,
		int favoriteCount,
		String image,
		LocalDate deathAnniversary
) {

}
