package com.rainbowletter.server.letter.application.port;

import com.rainbowletter.server.letter.domain.Letter;
import com.rainbowletter.server.letter.dto.LetterAdminPageRequest;
import com.rainbowletter.server.letter.dto.LetterAdminPageResponse;
import com.rainbowletter.server.letter.dto.LetterAdminRecentResponse;
import com.rainbowletter.server.letter.dto.LetterBoxRequest;
import com.rainbowletter.server.letter.dto.LetterBoxResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface LetterRepository {

	Letter findByIdAndUserIdOrElseThrow(Long id, Long userId);

	Letter findByIdOrElseThrow(Long id);

	Letter findByEmailAndIdOrElseThrow(String email, Long id);

	Letter findByShareLinkOrElseThrow(UUID shareLink);

	List<Letter> findAllByPetId(Long petId);

	List<LetterBoxResponse> findAllLetterBox(LetterBoxRequest request);

	List<LetterAdminRecentResponse> findAllRecentByUserId(Long userId);

	Page<LetterAdminPageResponse> findAllByAdmin(LetterAdminPageRequest request);

	Letter save(Letter letter);

	Long countByUserId(Long userId);

	Long countByPetId(Long petId);

	Integer getLastNumberByEmailAndPetId(String email, Long petId);

	void delete(Letter letter);

	void deleteAll(List<Letter> letters);

}
