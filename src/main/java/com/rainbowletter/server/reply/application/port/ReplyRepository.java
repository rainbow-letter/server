package com.rainbowletter.server.reply.application.port;

import com.rainbowletter.server.reply.domain.Reply;
import com.rainbowletter.server.reply.domain.ReplyStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReplyRepository {

	Reply findByIdOrElseThrow(Long id);

	Optional<Reply> findByLetterIdAndStatus(Long letterId, ReplyStatus status);

	Reply findByShareLinkOrElseThrow(UUID shareLink);

	List<Reply> findAllByReservation();

	Reply save(Reply reply);

	void saveAll(List<Reply> replies);

	boolean existsByLetterId(Long letterId);

	void deleteByLetterId(Long letterId);

}
