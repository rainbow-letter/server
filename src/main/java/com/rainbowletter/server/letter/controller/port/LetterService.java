package com.rainbowletter.server.letter.controller.port;

import com.rainbowletter.server.letter.dto.LetterCreate;
import com.rainbowletter.server.letter.dto.LetterResponse;
import java.util.UUID;

public interface LetterService {

	LetterResponse findByShareLink(UUID shareLink);

	Long create(String email, Long petId, LetterCreate letterCreate);

	void delete(String email, Long id);

}
