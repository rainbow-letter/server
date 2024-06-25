package com.rainbowletter.server.common.config.security.uri;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccessAllowUri implements AllowUri {
	ROOT("/"),
	INDEX("/index.html"),
	GET_FAQS("/api/faqs"),
	GET_IMAGES("/api/images/resources/**"),
	SHARE_LETTER("/api/letters/share/**"),
	SHARE_REPLY("/api/replies/share/**"),
	;

	private final String uri;

}
