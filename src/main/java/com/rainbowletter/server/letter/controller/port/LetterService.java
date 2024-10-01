package com.rainbowletter.server.letter.controller.port;

import com.rainbowletter.server.letter.dto.LetterAdminPageRequest;
import com.rainbowletter.server.letter.dto.LetterAdminPageResponse;
import com.rainbowletter.server.letter.dto.LetterAdminRecentResponse;
import com.rainbowletter.server.letter.dto.LetterBoxRequest;
import com.rainbowletter.server.letter.dto.LetterBoxResponses;
import com.rainbowletter.server.letter.dto.LetterCreate;
import com.rainbowletter.server.letter.dto.LetterResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface LetterService {

	LetterBoxResponses findAllLetterBox(LetterBoxRequest request);

	LetterResponse findByEmailAndId(String email, Long id);

	LetterResponse findById(Long id);

	LetterResponse findByShareLink(UUID shareLink);

	List<LetterAdminRecentResponse> findAllRecentByUserId(Long userId);

	Page<LetterAdminPageResponse> findAllByAdmin(LetterAdminPageRequest request);

	Long countByUserId(Long userId);

	Long create(String email, Long petId, LetterCreate letterCreate);

	void delete(String email, Long id);

}
