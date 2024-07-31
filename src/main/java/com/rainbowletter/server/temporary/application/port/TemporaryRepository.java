package com.rainbowletter.server.temporary.application.port;

import com.rainbowletter.server.temporary.domain.Temporary;

public interface TemporaryRepository {

	Temporary findByIdAndEmailOrElseThrow(Long id, String email);

	Temporary findByEmailAndPetIdOrElseThrow(String email, Long petId);

	boolean existsByEmailAndPetId(String email, Long petId);

	Temporary save(Temporary temporary);

}
