package com.rainbowletter.server.temporary.domain;

import com.rainbowletter.server.common.exception.RainbowLetterException;
import com.rainbowletter.server.pet.application.port.PetRepository;
import com.rainbowletter.server.temporary.application.port.TemporaryRepository;
import com.rainbowletter.server.user.application.port.UserRepository;
import com.rainbowletter.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TemporaryValidator {

	private final UserRepository userRepository;
	private final PetRepository petRepository;
	private final TemporaryRepository temporaryRepository;

	public void requireNonExists(final String email, final Long petId) {
		if (temporaryRepository.existsByEmailAndPetId(email, petId)) {
			throw new RainbowLetterException("임시저장 데이터가 존재합니다.");
		}
	}

	public void validatePetOwner(final String email, final Long petId) {
		final User user = userRepository.findByEmailOrElseThrow(email);
		petRepository.findByIdAndUserIdOrElseThrow(petId, user.getId());
	}

	public void validatePetOwner(final User user, final Long petId) {
		petRepository.findByIdAndUserIdOrElseThrow(petId, user.getId());
	}

}
