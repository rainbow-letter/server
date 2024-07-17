package com.rainbowletter.server.reply.infrastructure;

import static com.rainbowletter.server.reply.domain.QReply.reply;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rainbowletter.server.common.exception.RainbowLetterException;
import com.rainbowletter.server.reply.application.port.ReplyRepository;
import com.rainbowletter.server.reply.domain.Reply;
import com.rainbowletter.server.reply.domain.ReplyStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReplyRepositoryImpl implements ReplyRepository {

	private final JPAQueryFactory queryFactory;
	private final ReplyJpaRepository replyJpaRepository;

	@Override
	public Reply findByIdOrElseThrow(final Long id) {
		return replyJpaRepository.findById(id)
				.orElseThrow(() -> new RainbowLetterException("답장을 찾을 수 없습니다.", "id: [%d]".formatted(id)));
	}

	@Override
	public List<Reply> findAllByReservation() {
		return queryFactory.selectFrom(reply)
				.where(reply.inspection.isTrue().and(reply.status.eq(ReplyStatus.CHAT_GPT)))
				.fetch();
	}

	@Override
	public Reply save(final Reply reply) {
		return replyJpaRepository.save(reply);
	}

	@Override
	public void saveAll(final List<Reply> replies) {
		replyJpaRepository.saveAll(replies);
	}

	@Override
	public boolean existsByLetterId(final Long letterId) {
		return replyJpaRepository.existsByLetterId(letterId);
	}

	@Override
	public void deleteByLetterId(final Long letterId) {
		replyJpaRepository.deleteByLetterId(letterId);
	}

}
