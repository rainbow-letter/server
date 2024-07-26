package com.rainbowletter.server.letter.controller;

import static org.springframework.data.domain.Sort.Direction.DESC;

import com.rainbowletter.server.letter.controller.port.LetterService;
import com.rainbowletter.server.letter.dto.LetterAdminPageRequest;
import com.rainbowletter.server.letter.dto.LetterAdminPageResponse;
import com.rainbowletter.server.reply.domain.ReplyStatus;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/letters")
public class LetterAdminController {

	private final LetterService letterService;

	@GetMapping("/list")
	public ResponseEntity<Page<LetterAdminPageResponse>> findAllByAdmins(
			@RequestParam(value = "start") final LocalDate start,
			@RequestParam(value = "end") final LocalDate end,
			@RequestParam(value = "status", required = false) final ReplyStatus status,
			@RequestParam(value = "email", required = false) final String email,
			@RequestParam(value = "inspect", required = false) final Boolean inspect,
			@PageableDefault(sort = "createdAt", direction = DESC) final Pageable pageable
	) {
		final var request = LetterAdminPageRequest.of(start, end, status, email, inspect, pageable);
		final Page<LetterAdminPageResponse> response = letterService.findAllByAdmins(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
