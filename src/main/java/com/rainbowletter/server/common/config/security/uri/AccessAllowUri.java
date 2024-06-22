package com.rainbowletter.server.common.config.security.uri;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccessAllowUri implements AllowUri {
	GET_FAQS("/api/faqs"),
	GET_IMAGES("/api/images/resources/**"),
	;

	private final String uri;

}
