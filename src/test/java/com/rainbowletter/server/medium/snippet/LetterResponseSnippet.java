package com.rainbowletter.server.medium.snippet;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;

import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.snippet.Snippet;

public class LetterResponseSnippet {

	public static final Snippet LETTER_CREATE_RESPONSE_HEADER = responseHeaders(
			headerWithName(HttpHeaders.LOCATION).description("생성된 편지 ID")
	);

}
