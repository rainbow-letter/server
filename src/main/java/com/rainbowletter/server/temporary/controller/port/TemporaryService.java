package com.rainbowletter.server.temporary.controller.port;

import com.rainbowletter.server.temporary.dto.TemporaryCreate;
import com.rainbowletter.server.temporary.dto.TemporaryCreateResponse;
import com.rainbowletter.server.temporary.dto.TemporaryResponse;
import com.rainbowletter.server.temporary.dto.TemporarySessionResponse;
import com.rainbowletter.server.temporary.dto.TemporaryUpdate;

public interface TemporaryService {

	boolean existsByEmailAndPetId(String email, Long petId);

	TemporaryResponse findByEmailAndPetId(String email, Long petId);

	TemporaryCreateResponse create(String email, TemporaryCreate temporaryCreate);

	void update(String email, Long id, TemporaryUpdate temporaryUpdate);

	TemporarySessionResponse changeSession(String email, Long id);

	void delete(String email, Long id, Long petId);

}
