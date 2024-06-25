package com.rainbowletter.server.letter.controller.port;

import com.rainbowletter.server.letter.dto.LetterCreate;

public interface LetterService {

	Long create(String email, Long petId, LetterCreate letterCreate);

	void delete(String email, Long id);

}
