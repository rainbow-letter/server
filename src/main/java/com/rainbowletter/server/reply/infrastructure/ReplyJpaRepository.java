package com.rainbowletter.server.reply.infrastructure;

import com.rainbowletter.server.reply.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyJpaRepository extends JpaRepository<Reply, Long> {

	boolean existsByLetterId(Long letterId);

	void deleteByLetterId(Long letterId);

}
