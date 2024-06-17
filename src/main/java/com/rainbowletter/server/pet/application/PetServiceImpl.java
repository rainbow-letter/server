package com.rainbowletter.server.pet.application;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.pet.application.port.PetRepository;
import com.rainbowletter.server.pet.controller.port.PetService;
import com.rainbowletter.server.pet.domain.Pet;
import com.rainbowletter.server.pet.dto.PetCreate;
import com.rainbowletter.server.pet.dto.PetResponse;
import com.rainbowletter.server.pet.dto.PetResponses;
import com.rainbowletter.server.pet.dto.PetUpdate;
import com.rainbowletter.server.user.application.port.UserRepository;
import com.rainbowletter.server.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetServiceImpl implements PetService {

	private final TimeHolder timeHolder;
	private final PetRepository petRepository;
	private final UserRepository userRepository;

	@Override
	public PetResponse findByEmailAndId(final String email, final Long id) {
		final Pet pet = findPetByEmailAndIdOrElseThrow(email, id);
		return PetResponse.from(pet);
	}

	@Override
	public PetResponses findAllByEmail(final String email) {
		final User user = userRepository.findByEmailOrElseThrow(email);
		final List<Pet> pets = petRepository.findAllByUserId(user.getId());
		return PetResponses.from(pets);
	}

	@Override
	@Transactional
	public Long create(final String email, final PetCreate petCreate) {
		final User user = userRepository.findByEmailOrElseThrow(email);
		final Pet pet = new Pet(user, petCreate, timeHolder);
		petRepository.save(pet);
		return pet.getId();
	}

	@Override
	@Transactional
	public void increaseFavorite(final String email, final Long id) {
		final Pet pet = findPetByEmailAndIdOrElseThrow(email, id);
		pet.increaseFavorite(timeHolder);
		petRepository.save(pet);
	}

	@Override
	@Transactional
	public void update(final String email, final Long id, final PetUpdate petUpdate) {
		final Pet pet = findPetByEmailAndIdOrElseThrow(email, id);
		pet.update(petUpdate);
		petRepository.save(pet);
	}

	@Override
	@Transactional
	public void delete(final String email, final Long id) {
		final Pet pet = findPetByEmailAndIdOrElseThrow(email, id);
		pet.delete();
		petRepository.delete(pet);
	}

	private Pet findPetByEmailAndIdOrElseThrow(final String email, final Long id) {
		final User user = userRepository.findByEmailOrElseThrow(email);
		return petRepository.findByIdAndUserIdOrElseThrow(id, user.getId());
	}

}
