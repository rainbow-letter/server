package com.rainbowletter.server.reply.application.port;

import com.rainbowletter.server.reply.domain.Reply;
import java.util.List;
import java.util.UUID;

public interface ReplyRepository {

	Reply findByIdOrElseThrow(Long id);

	Reply findByShareLinkOrElseThrow(UUID shareLink);

	List<Reply> findAllByReservation();

	Reply save(Reply reply);

	void saveAll(List<Reply> replies);

	boolean existsByLetterId(Long letterId);

	void deleteByLetterId(Long letterId);

}
