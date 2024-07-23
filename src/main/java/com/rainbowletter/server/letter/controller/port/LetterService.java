package com.rainbowletter.server.letter.controller.port;

import com.rainbowletter.server.letter.dto.LetterBoxResponses;
import com.rainbowletter.server.letter.dto.LetterCreate;
import com.rainbowletter.server.letter.dto.LetterResponse;
import java.util.UUID;

public interface LetterService {

	LetterResponse findByShareLink(UUID shareLink);

	LetterBoxResponses findAllLetterBoxByEmail(String email);

	Long create(String email, Long petId, LetterCreate letterCreate);

	void delete(String email, Long id);

}
