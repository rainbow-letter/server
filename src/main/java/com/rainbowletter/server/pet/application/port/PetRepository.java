package com.rainbowletter.server.pet.application.port;

import com.rainbowletter.server.pet.domain.Pet;
import com.rainbowletter.server.pet.dto.PetDashboardResponses;
import java.util.List;
import java.util.UUID;

public interface PetRepository {

	Pet save(Pet pet);

	Pet findByIdAndUserIdOrElseThrow(Long id, Long userId);

	Pet findByLetterIdOrElseThrow(Long letterId);

	Pet findByShareLinkOrElseThrow(UUID shareLink);

	PetDashboardResponses findDashboardByEmail(String email);

	List<Pet> findAllByUserId(Long userId);

	void delete(Pet pet);

	void deleteAll(List<Pet> pets);

	void resetFavorite();

}
