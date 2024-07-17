package com.rainbowletter.server.reply.application.port;

import com.rainbowletter.server.reply.domain.Reply;
import java.util.List;

public interface ReplyRepository {

	Reply findByIdOrElseThrow(Long id);

	List<Reply> findAllByReservation();

	Reply save(Reply reply);

	void saveAll(List<Reply> replies);

	boolean existsByLetterId(Long letterId);

	void deleteByLetterId(Long letterId);

}
