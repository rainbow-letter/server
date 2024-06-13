package com.rainbowletter.server.medium.snippet;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;

import org.springframework.restdocs.snippet.Snippet;

public class CommonRequestSnippet {

	public static final Snippet AUTHORIZATION_HEADER = requestHeaders(
			headerWithName("Authorization").description("액세스 토큰")
	);

}
