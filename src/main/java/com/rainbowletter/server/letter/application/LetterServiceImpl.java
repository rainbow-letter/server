package com.rainbowletter.server.letter.application;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.application.port.UuidHolder;
import com.rainbowletter.server.letter.application.port.LetterRepository;
import com.rainbowletter.server.letter.controller.port.LetterService;
import com.rainbowletter.server.letter.domain.Letter;
import com.rainbowletter.server.letter.dto.LetterCreate;
import com.rainbowletter.server.pet.application.port.PetRepository;
import com.rainbowletter.server.pet.domain.Pet;
import com.rainbowletter.server.user.application.port.UserRepository;
import com.rainbowletter.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LetterServiceImpl implements LetterService {

	private final UuidHolder uuidHolder;
	private final TimeHolder timeHolder;
	private final PetRepository petRepository;
	private final UserRepository userRepository;
	private final LetterRepository letterRepository;

	@Override
	@Transactional
	public Long create(final String email, final Long petId, final LetterCreate letterCreate) {
		final User user = findUserByEmailOrElseThrow(email);
		final Pet pet = findPetByIdAndUserIdOrElseThrow(petId, user.getId());
		final Letter letter = new Letter(user.getId(), pet.getId(), letterCreate, uuidHolder, timeHolder);
		letterRepository.save(letter);
		return letter.getId();
	}

	@Override
	@Transactional
	public void delete(final String email, final Long id) {
		final User user = findUserByEmailOrElseThrow(email);
		final Letter letter = letterRepository.findByIdAndUserIdOrElseThrow(id, user.getId());
		letter.delete();
		letterRepository.delete(letter);
	}

	private User findUserByEmailOrElseThrow(final String email) {
		return userRepository.findByEmailOrElseThrow(email);
	}

	private Pet findPetByIdAndUserIdOrElseThrow(final Long id, final Long userId) {
		return petRepository.findByIdAndUserIdOrElseThrow(id, userId);
	}

}
