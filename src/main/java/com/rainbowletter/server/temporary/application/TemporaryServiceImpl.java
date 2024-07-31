package com.rainbowletter.server.temporary.application;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.application.port.UuidHolder;
import com.rainbowletter.server.temporary.application.port.TemporaryRepository;
import com.rainbowletter.server.temporary.controller.port.TemporaryService;
import com.rainbowletter.server.temporary.domain.Temporary;
import com.rainbowletter.server.temporary.domain.TemporaryValidator;
import com.rainbowletter.server.temporary.dto.TemporaryCreate;
import com.rainbowletter.server.temporary.dto.TemporaryCreateResponse;
import com.rainbowletter.server.temporary.dto.TemporaryResponse;
import com.rainbowletter.server.temporary.dto.TemporarySessionResponse;
import com.rainbowletter.server.temporary.dto.TemporaryUpdate;
import com.rainbowletter.server.user.application.port.UserRepository;
import com.rainbowletter.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TemporaryServiceImpl implements TemporaryService {

	private final UuidHolder uuidHolder;
	private final TimeHolder timeHolder;
	private final UserRepository userRepository;
	private final TemporaryValidator temporaryValidator;
	private final TemporaryRepository temporaryRepository;

	@Override
	public boolean existsByEmailAndPetId(final String email, final Long petId) {
		return temporaryRepository.existsByEmailAndPetId(email, petId);
	}

	@Override
	public TemporaryResponse findByEmailAndPetId(final String email, final Long petId) {
		final Temporary temporary = temporaryRepository.findByEmailAndPetIdOrElseThrow(email, petId);
		return TemporaryResponse.from(temporary);
	}

	@Override
	@Transactional
	public TemporaryCreateResponse create(final String email, final TemporaryCreate temporaryCreate) {
		final User user = userRepository.findByEmailOrElseThrow(email);
		temporaryValidator.requireNonExists(email, temporaryCreate.petId());
		temporaryValidator.validatePetOwner(user, temporaryCreate.petId());

		final Temporary temporary = new Temporary(user.getId(), temporaryCreate, uuidHolder, timeHolder);
		temporaryRepository.save(temporary);
		return TemporaryCreateResponse.from(temporary);
	}

	@Override
	@Transactional
	public void update(final String email, final Long id, final TemporaryUpdate temporaryUpdate) {
		temporaryValidator.validatePetOwner(email, temporaryUpdate.petId());
		final Temporary temporary = temporaryRepository.findByIdAndEmailOrElseThrow(id, email);
		temporary.update(temporaryUpdate);
		temporaryRepository.save(temporary);
	}

	@Override
	@Transactional
	public TemporarySessionResponse changeSession(final String email, final Long id) {
		final Temporary temporary = temporaryRepository.findByIdAndEmailOrElseThrow(id, email);
		temporary.changeSession(uuidHolder);
		temporaryRepository.save(temporary);
		return TemporarySessionResponse.from(temporary);
	}

	@Override
	@Transactional
	public void delete(final String email, final Long id, final Long petId) {
		temporaryValidator.validatePetOwner(email, petId);
		final Temporary temporary = temporaryRepository.findByIdAndEmailOrElseThrow(id, email);
		temporary.delete();
		temporaryRepository.save(temporary);
	}

}
