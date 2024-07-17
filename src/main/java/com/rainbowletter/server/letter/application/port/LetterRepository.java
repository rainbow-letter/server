package com.rainbowletter.server.letter.application.port;

import com.rainbowletter.server.letter.domain.Letter;
import java.util.List;
import java.util.UUID;

public interface LetterRepository {

	Letter findByIdAndUserIdOrElseThrow(Long id, Long userId);

	Letter findByIdOrElseThrow(Long id);

	Letter findByShareLinkOrElseThrow(UUID shareLink);

	List<Letter> findAllByPetId(Long petId);

	Letter save(Letter letter);

	Long countByPetId(Long petId);

	void delete(Letter letter);

	void deleteAll(List<Letter> letters);

}
