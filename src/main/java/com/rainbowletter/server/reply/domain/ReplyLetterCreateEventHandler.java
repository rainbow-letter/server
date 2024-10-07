package com.rainbowletter.server.reply.domain;

import com.rainbowletter.server.letter.domain.LetterCreateEvent;
import com.rainbowletter.server.reply.application.port.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("!test")
@Component
@RequiredArgsConstructor
public class ReplyLetterCreateEventHandler {

	private final ReplyGenerator replyGenerator;
	private final ReplyRepository replyRepository;

	@Async
	@EventListener
	@Transactional
	public void handle(final LetterCreateEvent event) {
		final Reply reply = replyGenerator.generate(event.letter());
		replyRepository.save(reply);
	}

}
