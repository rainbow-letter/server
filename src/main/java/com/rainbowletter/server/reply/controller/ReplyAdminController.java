package com.rainbowletter.server.reply.controller;

import com.rainbowletter.server.reply.controller.port.ReplyService;
import com.rainbowletter.server.reply.dto.ReplyUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/replies")
public class ReplyAdminController {

	private final ReplyService replyService;

	@PostMapping("/generate/{letterId}")
	public ResponseEntity<Void> generate(@PathVariable("letterId") final Long letterId) {
		replyService.generate(letterId);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("/inspect/{id}")
	public ResponseEntity<Void> inspect(@PathVariable("id") final Long id) {
		replyService.inspect(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/submit/{id}")
	public ResponseEntity<Void> submit(@PathVariable("id") final Long id) {
		replyService.submit(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(
			@PathVariable("id") final Long id,
			@RequestBody @Valid final ReplyUpdateRequest request
	) {
		replyService.update(id, request.toDomainDto());
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
