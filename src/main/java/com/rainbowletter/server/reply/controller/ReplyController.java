package com.rainbowletter.server.reply.controller;

import com.rainbowletter.server.reply.controller.port.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/replies")
public class ReplyController {

	private final ReplyService replyService;

	@PostMapping("/read/{id}")
	public ResponseEntity<Void> read(@PathVariable("id") final Long id) {
		replyService.read(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
