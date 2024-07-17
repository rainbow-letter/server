package com.rainbowletter.server.reply.controller.port;

import com.rainbowletter.server.reply.dto.ReplyUpdate;

public interface ReplyService {

	void generate(Long letterId);

	void inspect(Long id);

	void submit(Long id);

	void read(Long id);

	void update(Long id, ReplyUpdate replyUpdate);

}
