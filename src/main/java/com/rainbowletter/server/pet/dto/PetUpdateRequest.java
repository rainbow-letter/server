package com.rainbowletter.server.pet.dto;

import static com.rainbowletter.server.common.validation.ValidationMessage.PAST_OR_PRESENT_MESSAGE;

import com.rainbowletter.server.common.validation.PetName;
import com.rainbowletter.server.common.validation.PetOwner;
import com.rainbowletter.server.common.validation.PetPersonalities;
import com.rainbowletter.server.common.validation.PetPersonality;
import com.rainbowletter.server.common.validation.PetSpecies;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Set;

public record PetUpdateRequest(
		@PetName
		String name,

		@PetSpecies
		String species,

		@PetOwner
		String owner,

		@PetPersonalities
		Set<@PetPersonality String> personalities,

		@Nullable
		@PastOrPresent(message = PAST_OR_PRESENT_MESSAGE)
		LocalDate deathAnniversary,

		@Nullable
		String image
) {

	public PetUpdate toDomainDto() {
		return PetUpdate.of(name, species, owner, personalities.stream().toList(), deathAnniversary, image);
	}

}
