package com.rainbowletter.server.pet.dto;

import com.rainbowletter.server.pet.domain.Pet;
import java.util.List;

public record PetResponses(List<PetResponse> pets) {

	public static PetResponses from(final List<Pet> pets) {
		return new PetResponses(
				pets.stream()
						.map(PetResponse::from)
						.toList()
		);
	}

}
