package com.rainbowletter.server.letter.application.port;

import com.rainbowletter.server.letter.domain.Letter;
import java.util.List;

public interface LetterRepository {

	Letter findByIdAndUserIdOrElseThrow(Long id, Long userId);

	List<Letter> findAllByPetId(Long petId);

	Letter save(Letter letter);

	void delete(Letter letter);

	void deleteAll(List<Letter> letters);

}
