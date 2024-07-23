package com.rainbowletter.server.reply.application;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.reply.application.port.ReplyRepository;
import com.rainbowletter.server.reply.controller.port.ReplyService;
import com.rainbowletter.server.reply.domain.Reply;
import com.rainbowletter.server.reply.domain.ReplyGenerator;
import com.rainbowletter.server.reply.domain.ReplyValidator;
import com.rainbowletter.server.reply.dto.ReplyResponse;
import com.rainbowletter.server.reply.dto.ReplyUpdate;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyServiceImpl implements ReplyService {

	private final TimeHolder timeHolder;
	private final ReplyGenerator replyGenerator;
	private final ReplyValidator replyValidator;
	private final ReplyRepository replyRepository;

	@Override
	public ReplyResponse findByLetterId(final Long letterId) {
		final Optional<Reply> reply = replyRepository.findByLetterId(letterId);
		return reply.map(ReplyResponse::from).orElse(null);
	}

	@Override
	public ReplyResponse findByShareLink(final UUID shareLink) {
		final Reply reply = replyRepository.findByShareLinkOrElseThrow(shareLink);
		return ReplyResponse.from(reply);
	}

	@Override
	@Transactional
	public void generate(final Long letterId) {
		final boolean isReplyExists = replyRepository.existsByLetterId(letterId);
		if (isReplyExists) {
			replyRepository.deleteByLetterId(letterId);
		}

		final Reply reply = replyGenerator.generate(letterId);
		replyRepository.save(reply);
	}

	@Override
	@Transactional
	public void inspect(final Long id) {
		final Reply reply = replyRepository.findByIdOrElseThrow(id);
		reply.inspect(replyValidator, timeHolder);
		replyRepository.save(reply);
	}

	@Override
	@Transactional
	public void submit(final Long id) {
		final Reply reply = replyRepository.findByIdOrElseThrow(id);
		reply.submit(replyValidator, timeHolder);
		replyRepository.save(reply);
	}

	@Override
	@Transactional
	public void read(final Long id) {
		final Reply reply = replyRepository.findByIdOrElseThrow(id);
		reply.read(replyValidator);
		replyRepository.save(reply);
	}

	@Override
	@Transactional
	public void update(final Long id, final ReplyUpdate replyUpdate) {
		final Reply reply = replyRepository.findByIdOrElseThrow(id);
		reply.update(replyUpdate);
		replyRepository.save(reply);
	}

}
