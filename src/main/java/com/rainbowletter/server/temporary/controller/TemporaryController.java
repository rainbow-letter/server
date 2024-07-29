package com.rainbowletter.server.temporary.controller;

import com.rainbowletter.server.common.infrastructure.SecurityUtils;
import com.rainbowletter.server.temporary.controller.port.TemporaryService;
import com.rainbowletter.server.temporary.dto.TemporaryCreateRequest;
import com.rainbowletter.server.temporary.dto.TemporaryCreateResponse;
import com.rainbowletter.server.temporary.dto.TemporaryExistsResponse;
import com.rainbowletter.server.temporary.dto.TemporaryResponse;
import com.rainbowletter.server.temporary.dto.TemporarySessionResponse;
import com.rainbowletter.server.temporary.dto.TemporaryUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/temporaries")
public class TemporaryController {

	private final TemporaryService temporaryService;

	@GetMapping("/exists")
	public ResponseEntity<TemporaryExistsResponse> exists(@RequestParam("pet") final Long petId) {
		final String email = SecurityUtils.getEmail();
		final boolean exists = temporaryService.existsByEmailAndPetId(email, petId);
		return new ResponseEntity<>(TemporaryExistsResponse.from(exists), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<TemporaryResponse> findByPetId(@RequestParam("pet") final Long petId) {
		final String email = SecurityUtils.getEmail();
		final TemporaryResponse response = temporaryService.findByEmailAndPetId(email, petId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<TemporaryCreateResponse> create(@RequestBody @Valid final TemporaryCreateRequest request) {
		final String email = SecurityUtils.getEmail();
		final TemporaryCreateResponse response = temporaryService.create(email, request.toDomainDto());
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(
			@PathVariable("id") final Long id,
			@RequestBody @Valid final TemporaryUpdateRequest request
	) {
		final String email = SecurityUtils.getEmail();
		temporaryService.update(email, id, request.toDomainDto());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/session/{id}")
	public ResponseEntity<TemporarySessionResponse> changeSession(@PathVariable("id") final Long id) {
		final String email = SecurityUtils.getEmail();
		final TemporarySessionResponse sessionId = temporaryService.changeSession(email, id);
		return new ResponseEntity<>(sessionId, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") final Long id, @RequestParam("pet") final Long petId) {
		final String email = SecurityUtils.getEmail();
		temporaryService.delete(email, id, petId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
