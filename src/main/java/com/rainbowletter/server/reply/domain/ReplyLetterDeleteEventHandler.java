package com.rainbowletter.server.reply.domain;

import com.rainbowletter.server.letter.domain.LetterDeleteEvent;
import com.rainbowletter.server.reply.application.port.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReplyLetterDeleteEventHandler {

	private final ReplyRepository replyRepository;

	@Async
	@EventListener
	@Transactional
	public void handle(final LetterDeleteEvent event) {
		replyRepository.deleteByLetterId(event.letter().getId());
	}

}
