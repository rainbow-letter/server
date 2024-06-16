package com.rainbowletter.server.faq.controller;

import com.rainbowletter.server.faq.controller.port.FaqService;
import com.rainbowletter.server.faq.dto.FaqUserResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/faqs")
public class FaqController {

	private final FaqService faqService;

	@GetMapping
	public ResponseEntity<FaqUserResponses> findAllByVisibility() {
		final FaqUserResponses responses = faqService.findAllByVisibility();
		return ResponseEntity.ok(responses);
	}

}
