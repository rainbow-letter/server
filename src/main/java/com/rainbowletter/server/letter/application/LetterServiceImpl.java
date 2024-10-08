package com.rainbowletter.server.letter.application;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.application.port.UuidHolder;
import com.rainbowletter.server.common.domain.EventLogger;
import com.rainbowletter.server.letter.application.port.LetterRepository;
import com.rainbowletter.server.letter.controller.port.LetterService;
import com.rainbowletter.server.letter.domain.Letter;
import com.rainbowletter.server.letter.dto.LetterAdminPageRequest;
import com.rainbowletter.server.letter.dto.LetterAdminPageResponse;
import com.rainbowletter.server.letter.dto.LetterAdminRecentResponse;
import com.rainbowletter.server.letter.dto.LetterBoxRequest;
import com.rainbowletter.server.letter.dto.LetterBoxResponse;
import com.rainbowletter.server.letter.dto.LetterBoxResponses;
import com.rainbowletter.server.letter.dto.LetterCreate;
import com.rainbowletter.server.letter.dto.LetterResponse;
import com.rainbowletter.server.pet.application.port.PetRepository;
import com.rainbowletter.server.pet.domain.Pet;
import com.rainbowletter.server.user.application.port.UserRepository;
import com.rainbowletter.server.user.domain.User;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
	private final EventLogger eventLogger;

	@Override
	public LetterBoxResponses findAllLetterBox(final LetterBoxRequest request) {
		final List<LetterBoxResponse> letterBoxResponses = letterRepository.findAllLetterBox(request);
		return LetterBoxResponses.from(letterBoxResponses);
	}

	@Override
	public LetterResponse findByEmailAndId(final String email, final Long id) {
		final Letter letter = letterRepository.findByEmailAndIdOrElseThrow(email, id);
		return LetterResponse.from(letter);
	}

	@Override
	public LetterResponse findById(final Long id) {
		final Letter letter = letterRepository.findByIdOrElseThrow(id);
		return LetterResponse.from(letter);
	}

	@Override
	public LetterResponse findByShareLink(final UUID shareLink) {
		final Letter letter = letterRepository.findByShareLinkOrElseThrow(shareLink);
		return LetterResponse.from(letter);
	}

	@Override
	public List<LetterAdminRecentResponse> findAllRecentByUserId(final Long userId) {
		return letterRepository.findAllRecentByUserId(userId);
	}

	@Override
	public Page<LetterAdminPageResponse> findAllByAdmin(final LetterAdminPageRequest request) {
		return letterRepository.findAllByAdmin(request);
	}

	@Override
	public Long countByUserId(final Long userId) {
		return letterRepository.countByUserId(userId);
	}

	@Override
	@Transactional
	public Long create(final String email, final Long petId, final LetterCreate letterCreate) {
		final User user = findUserByEmailOrElseThrow(email);
		final Pet pet = findPetByIdAndUserIdOrElseThrow(petId, user.getId());
		final Integer lastNumber = letterRepository.getLastNumberByEmailAndPetId(email, petId);
		final Letter letter = new Letter(user.getId(), pet.getId(), lastNumber, letterCreate, uuidHolder, timeHolder);
		letterRepository.save(letter);
		return letter.getId();
	}

	@Override
	@Transactional
	public void delete(final String email, final Long id) {
		final User user = findUserByEmailOrElseThrow(email);
		final Letter letter = letterRepository.findByIdAndUserIdOrElseThrow(id, user.getId());
		letter.delete(eventLogger, timeHolder);
		letterRepository.delete(letter);
	}

	private User findUserByEmailOrElseThrow(final String email) {
		return userRepository.findByEmailOrElseThrow(email);
	}

	private Pet findPetByIdAndUserIdOrElseThrow(final Long id, final Long userId) {
		return petRepository.findByIdAndUserIdOrElseThrow(id, userId);
	}

}
