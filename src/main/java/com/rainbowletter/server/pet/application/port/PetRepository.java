package com.rainbowletter.server.pet.application.port;

import com.rainbowletter.server.pet.domain.Pet;
import java.util.List;

public interface PetRepository {

	Pet save(Pet pet);

	Pet findByIdAndUserIdOrElseThrow(Long id, Long userId);

	List<Pet> findAllByUserId(Long userId);

	void delete(Pet pet);

	void resetFavorite();

}
