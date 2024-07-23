package com.rainbowletter.server.letter.controller;

import com.rainbowletter.server.common.infrastructure.SecurityUtils;
import com.rainbowletter.server.letter.controller.port.LetterService;
import com.rainbowletter.server.letter.dto.LetterBoxResponses;
import com.rainbowletter.server.letter.dto.LetterCreateRequest;
import com.rainbowletter.server.letter.dto.LetterDetailResponse;
import com.rainbowletter.server.letter.dto.LetterResponse;
import com.rainbowletter.server.pet.controller.port.PetService;
import com.rainbowletter.server.pet.dto.PetExcludeFavoriteResponse;
import com.rainbowletter.server.reply.controller.port.ReplyService;
import com.rainbowletter.server.reply.dto.ReplyResponse;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/letters")
public class LetterController {

	private final PetService petService;
	private final LetterService letterService;
	private final ReplyService replyService;

	@GetMapping("/box")
	public ResponseEntity<LetterBoxResponses> findAllLetterBoxByEmail() {
		final String email = SecurityUtils.getEmail();
		final LetterBoxResponses response = letterService.findAllLetterBoxByEmail(email);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/share/{shareLink}")
	public ResponseEntity<LetterDetailResponse> findByShareLink(@PathVariable("shareLink") final String shareLink) {
		final UUID shareUUID = UUID.fromString(shareLink);
		final PetExcludeFavoriteResponse petResponse = petService.findByShareLink(shareUUID);
		final LetterResponse letterResponse = letterService.findByShareLink(shareUUID);
		final ReplyResponse replyResponse = replyService.findByShareLink(shareUUID);
		final LetterDetailResponse response = LetterDetailResponse.of(petResponse, letterResponse, replyResponse);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Void> create(
			@RequestParam("pet") final Long petId,
			@RequestBody @Valid final LetterCreateRequest request
	) {
		final String email = SecurityUtils.getEmail();
		final Long id = letterService.create(email, petId, request.toDomainDto());
		return ResponseEntity.created(URI.create(id.toString())).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {
		final String email = SecurityUtils.getEmail();
		letterService.delete(email, id);
		return ResponseEntity.ok().build();
	}

}
