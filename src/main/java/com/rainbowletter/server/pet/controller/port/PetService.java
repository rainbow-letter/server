package com.rainbowletter.server.pet.controller.port;

import com.rainbowletter.server.pet.dto.PetCreate;
import com.rainbowletter.server.pet.dto.PetExcludeFavoriteResponse;
import com.rainbowletter.server.pet.dto.PetResponse;
import com.rainbowletter.server.pet.dto.PetResponses;
import com.rainbowletter.server.pet.dto.PetUpdate;
import java.util.UUID;

public interface PetService {

	PetResponse findByEmailAndId(String email, Long id);

	PetExcludeFavoriteResponse findByShareLink(UUID shareLink);

	PetResponses findAllByEmail(String email);

	Long create(String email, PetCreate petCreate);

	void increaseFavorite(String email, Long id);

	void update(String email, Long id, PetUpdate petUpdate);

	void delete(String email, Long id);

}
