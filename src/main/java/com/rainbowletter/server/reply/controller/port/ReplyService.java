package com.rainbowletter.server.reply.controller.port;

import com.rainbowletter.server.reply.dto.ReplyResponse;
import com.rainbowletter.server.reply.dto.ReplyUpdate;
import java.util.UUID;

public interface ReplyService {

	ReplyResponse findByShareLink(UUID shareLink);

	void generate(Long letterId);

	void inspect(Long id);

	void submit(Long id);

	void read(Long id);

	void update(Long id, ReplyUpdate replyUpdate);

}
