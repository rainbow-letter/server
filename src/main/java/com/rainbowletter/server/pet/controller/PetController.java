package com.rainbowletter.server.pet.controller;

import com.rainbowletter.server.common.infrastructure.SecurityUtils;
import com.rainbowletter.server.pet.controller.port.PetService;
import com.rainbowletter.server.pet.dto.PetCreateRequest;
import com.rainbowletter.server.pet.dto.PetResponse;
import com.rainbowletter.server.pet.dto.PetResponses;
import com.rainbowletter.server.pet.dto.PetUpdateRequest;
import jakarta.validation.Valid;
import java.net.URI;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pets")
public class PetController {

	private final PetService petService;

	@GetMapping
	public ResponseEntity<PetResponses> findAllByEmail() {
		final String email = SecurityUtils.getEmail();
		final PetResponses responses = petService.findAllByEmail(email);
		return new ResponseEntity<>(responses, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PetResponse> findByIdAndEmail(@PathVariable("id") final Long id) {
		final String email = SecurityUtils.getEmail();
		final PetResponse response = petService.findByEmailAndId(email, id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Void> create(@RequestBody @Valid final PetCreateRequest request) {
		final String email = SecurityUtils.getEmail();
		final Long id = petService.create(email, request.toDomainDto());
		return ResponseEntity.created(URI.create(id.toString())).build();
	}

	@PostMapping("/favorite/{id}")
	public ResponseEntity<Void> increaseFavorite(@PathVariable("id") final Long id) {
		final String email = SecurityUtils.getEmail();
		petService.increaseFavorite(email, id);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(
			@PathVariable("id") final Long id,
			@RequestBody @Valid final PetUpdateRequest request
	) {
		final String email = SecurityUtils.getEmail();
		petService.update(email, id, request.toDomainDto());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {
		final String email = SecurityUtils.getEmail();
		petService.delete(email, id);
		return ResponseEntity.ok().build();
	}

}
