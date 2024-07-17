package com.rainbowletter.server.pet.application.port;

import com.rainbowletter.server.pet.domain.Pet;
import java.util.List;
import java.util.UUID;

public interface PetRepository {

	Pet save(Pet pet);

	Pet findByIdAndUserIdOrElseThrow(Long id, Long userId);

	Pet findByShareLinkOrElseThrow(UUID shareLink);

	List<Pet> findAllByUserId(Long userId);

	void delete(Pet pet);

	void resetFavorite();

}
