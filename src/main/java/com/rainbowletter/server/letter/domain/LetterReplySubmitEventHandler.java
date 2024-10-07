package com.rainbowletter.server.letter.domain;

import com.rainbowletter.server.letter.application.port.LetterRepository;
import com.rainbowletter.server.reply.domain.ReplySubmitEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LetterReplySubmitEventHandler {

	private final LetterRepository letterRepository;

	@Async
	@EventListener
	@Transactional
	public void handle(final ReplySubmitEvent event) {
		final Letter letter = letterRepository.findByIdOrElseThrow(event.reply().getLetterId());
		letter.receiveReply();
		letterRepository.save(letter);
	}

}
