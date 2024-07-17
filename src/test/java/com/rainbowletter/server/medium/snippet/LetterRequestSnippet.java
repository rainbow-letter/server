package com.rainbowletter.server.medium.snippet;

import static com.rainbowletter.server.medium.RestDocsUtils.constraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class LetterRequestSnippet {

	public static final Snippet LETTER_PARAM_PET_ID = queryParameters(
			parameterWithName("pet").description("반려 동물 ID")
	);

	public static final Snippet LETTER_PATH_VARIABLE_ID = pathParameters(
			parameterWithName("id").description("편지 ID")
	);

	public static final Snippet LETTER_PATH_VARIABLE_SHARE_LINK = pathParameters(
			parameterWithName("shareLink").description("편지 공유 링크")
	);

	public static final Snippet LETTER_CREATE_REQUEST = requestFields(
			fieldWithPath("summary")
					.type(JsonFieldType.STRING)
					.description("편지 제목")
					.attributes(constraints("20자 이내")),
			fieldWithPath("content")
					.type(JsonFieldType.STRING)
					.description("편지 본문")
					.attributes(constraints("1000자 이내")),
			fieldWithPath("image")
					.type(JsonFieldType.STRING)
					.description("편지 이미지")
					.attributes(constraints("이미지 objectKey"))
					.optional()
	);

}
