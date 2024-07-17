package com.rainbowletter.server.reply.application;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.reply.application.port.ReplyRepository;
import com.rainbowletter.server.reply.domain.Reply;
import com.rainbowletter.server.reply.domain.ReplyValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReplyScheduler {

	private final TimeHolder timeHolder;
	private final ReplyValidator replyValidator;
	private final ReplyRepository replyRepository;

	@Async
	@Scheduled(cron = "0 0 10 * * *")
	@Transactional
	public void reservationSubmit() {
		final List<Reply> replies = replyRepository.findAllByReservation();
		replies.forEach(reply -> reply.submit(replyValidator, timeHolder));
		replyRepository.saveAll(replies);
	}

}
