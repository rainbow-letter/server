package com.rainbowletter.server.letter.controller;

import static org.springframework.data.domain.Sort.Direction.DESC;

import com.rainbowletter.server.letter.controller.port.LetterService;
import com.rainbowletter.server.letter.dto.LetterAdminDetailResponse;
import com.rainbowletter.server.letter.dto.LetterAdminPageRequest;
import com.rainbowletter.server.letter.dto.LetterAdminPageResponse;
import com.rainbowletter.server.letter.dto.LetterAdminRecentResponse;
import com.rainbowletter.server.letter.dto.LetterResponse;
import com.rainbowletter.server.pet.controller.port.PetService;
import com.rainbowletter.server.pet.dto.PetExcludeFavoriteResponse;
import com.rainbowletter.server.reply.controller.port.ReplyService;
import com.rainbowletter.server.reply.domain.ReplyStatus;
import com.rainbowletter.server.reply.dto.ReplyResponse;
import com.rainbowletter.server.user.controller.port.UserService;
import com.rainbowletter.server.user.dto.UserInformationResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/letters")
public class LetterAdminController {

	private final UserService userService;
	private final PetService petService;
	private final LetterService letterService;
	private final ReplyService replyService;

	@GetMapping("/list")
	public ResponseEntity<Page<LetterAdminPageResponse>> findAllByAdmin(
			@RequestParam(value = "start") final LocalDate start,
			@RequestParam(value = "end") final LocalDate end,
			@RequestParam(value = "status", required = false) final ReplyStatus status,
			@RequestParam(value = "email", required = false) final String email,
			@RequestParam(value = "inspect", required = false) final Boolean inspect,
			@PageableDefault(sort = "createdAt", direction = DESC) final Pageable pageable
	) {
		final var request = LetterAdminPageRequest.of(start, end, status, email, inspect, pageable);
		final Page<LetterAdminPageResponse> response = letterService.findAllByAdmin(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<LetterAdminDetailResponse> findByAdmin(
			@PathVariable("id") final Long id,
			@RequestParam("user") final Long userId
	) {
		final UserInformationResponse userResponse = userService.information(userId);
		final PetExcludeFavoriteResponse petResponse = petService.findByLetterId(id);
		final LetterResponse letterResponse = letterService.findById(id);
		final List<LetterAdminRecentResponse> recentResponses = letterService.findAllRecentByUserId(userId);
		final ReplyResponse replyResponse = replyService.findByLetterIdAndStatus(id, null);
		final LetterAdminDetailResponse response = LetterAdminDetailResponse.of(
				userResponse, petResponse, letterResponse, replyResponse, recentResponses);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
