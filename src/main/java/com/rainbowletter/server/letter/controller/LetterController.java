package com.rainbowletter.server.letter.controller;

import com.rainbowletter.server.common.infrastructure.SecurityUtils;
import com.rainbowletter.server.letter.controller.port.LetterService;
import com.rainbowletter.server.letter.dto.LetterCreateRequest;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	private final LetterService letterService;

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
