package com.rainbowletter.server.faq.controller;

import com.rainbowletter.server.faq.controller.port.FaqService;
import com.rainbowletter.server.faq.dto.FaqAdminResponses;
import com.rainbowletter.server.faq.dto.FaqCreateRequest;
import com.rainbowletter.server.faq.dto.FaqSwitchSequenceRequest;
import com.rainbowletter.server.faq.dto.FaqUpdateRequest;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/admins/faqs")
public class FaqAdminController {

	private final FaqService faqService;

	@GetMapping
	public ResponseEntity<FaqAdminResponses> findAll() {
		final FaqAdminResponses responses = faqService.findAll();
		return ResponseEntity.ok(responses);
	}

	@PostMapping
	public ResponseEntity<Void> create(@RequestBody @Valid final FaqCreateRequest request) {
		final Long id = faqService.create(request.toDomainDto());
		return ResponseEntity.created(URI.create(id.toString())).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(
			@PathVariable("id") final Long id,
			@RequestBody @Valid final FaqUpdateRequest request
	) {
		faqService.update(id, request.toDomainDto());
		return ResponseEntity.ok().build();
	}

	@PutMapping("/visibility/{id}")
	public ResponseEntity<Void> changeVisibility(@PathVariable("id") final Long id) {
		faqService.changeVisibility(id);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/switch-sequence")
	public ResponseEntity<Void> switchSequence(@RequestBody @Valid final FaqSwitchSequenceRequest request) {
		faqService.switchSequence(request.toDomainDto());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {
		faqService.delete(id);
		return ResponseEntity.ok().build();
	}

}
