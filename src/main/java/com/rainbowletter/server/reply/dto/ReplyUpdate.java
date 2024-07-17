package com.rainbowletter.server.reply.dto;

public record ReplyUpdate(String summary, String content) {

	public static ReplyUpdate of(final String summary, final String content) {
		return new ReplyUpdate(summary, content);
	}

}
