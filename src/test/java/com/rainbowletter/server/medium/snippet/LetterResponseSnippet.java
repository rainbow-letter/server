package com.rainbowletter.server.medium.snippet;

import static com.rainbowletter.server.medium.RestDocsUtils.constraints;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class LetterResponseSnippet {

	public static final Snippet LETTER_CREATE_RESPONSE_HEADER = responseHeaders(
			headerWithName(HttpHeaders.LOCATION).description("생성된 편지 ID")
	);

	public static final Snippet LETTER_RESPONSE = responseFields(
			fieldWithPath("pet.id")
					.type(JsonFieldType.NUMBER)
					.description("반려동물 ID"),
			fieldWithPath("pet.userId")
					.type(JsonFieldType.NUMBER)
					.description("사용자 ID"),
			fieldWithPath("pet.name")
					.type(JsonFieldType.STRING)
					.description("아이의 이름"),
			fieldWithPath("pet.species")
					.type(JsonFieldType.STRING)
					.description("아이의 종류"),
			fieldWithPath("pet.owner")
					.type(JsonFieldType.STRING)
					.description("주인을 부르는 호칭"),
			fieldWithPath("pet.personalities")
					.type(JsonFieldType.ARRAY)
					.description("아이의 성격"),
			fieldWithPath("pet.deathAnniversary")
					.type(JsonFieldType.STRING)
					.description("아이가 떠난 날")
					.optional(),
			fieldWithPath("pet.image")
					.type(JsonFieldType.STRING)
					.description("이미지의 objectKey")
					.optional(),
			fieldWithPath("pet.createdAt")
					.type(JsonFieldType.STRING)
					.description("생성일"),
			fieldWithPath("pet.updatedAt")
					.type(JsonFieldType.STRING)
					.description("수정일"),
			fieldWithPath("letter.id")
					.type(JsonFieldType.NUMBER)
					.description("편지 ID"),
			fieldWithPath("letter.userId")
					.type(JsonFieldType.NUMBER)
					.description("사용자 ID"),
			fieldWithPath("letter.petId")
					.type(JsonFieldType.NUMBER)
					.description("반려동물 ID"),
			fieldWithPath("letter.summary")
					.type(JsonFieldType.STRING)
					.description("편지 제목"),
			fieldWithPath("letter.content")
					.type(JsonFieldType.STRING)
					.description("편지 본문"),
			fieldWithPath("letter.shareLink")
					.type(JsonFieldType.STRING)
					.description("공유 링크 UUID"),
			fieldWithPath("letter.image")
					.type(JsonFieldType.STRING)
					.description("이미지의 objectKey")
					.optional(),
			fieldWithPath("letter.status")
					.type(JsonFieldType.STRING)
					.description("편지 답장 여부")
					.attributes(constraints("REQUEST || RESPONSE")),
			fieldWithPath("letter.createdAt")
					.type(JsonFieldType.STRING)
					.description("생성일"),
			fieldWithPath("letter.updatedAt")
					.type(JsonFieldType.STRING)
					.description("수정일"),
			fieldWithPath("reply.id")
					.type(JsonFieldType.NUMBER)
					.description("답장 ID"),
			fieldWithPath("reply.petId")
					.type(JsonFieldType.NUMBER)
					.description("반려동물 ID"),
			fieldWithPath("reply.letterId")
					.type(JsonFieldType.NUMBER)
					.description("편지 ID"),
			fieldWithPath("reply.summary")
					.type(JsonFieldType.STRING)
					.description("답장 제목"),
			fieldWithPath("reply.content")
					.type(JsonFieldType.STRING)
					.description("답장 본문"),
			fieldWithPath("reply.promptType")
					.type(JsonFieldType.STRING)
					.description("답장 GPT 프롬프트 타입")
					.attributes(constraints("A || B")),
			fieldWithPath("reply.inspection")
					.type(JsonFieldType.BOOLEAN)
					.description("답장 검수 여부"),
			fieldWithPath("reply.inspectionTime")
					.type(JsonFieldType.STRING)
					.description("답장 검수일")
					.optional(),
			fieldWithPath("reply.status")
					.type(JsonFieldType.STRING)
					.description("답장 상태")
					.attributes(constraints("CHAT_GPT || REPLY")),
			fieldWithPath("reply.submitTime")
					.type(JsonFieldType.STRING)
					.description("답장 발송일")
					.optional(),
			fieldWithPath("reply.readStatus")
					.type(JsonFieldType.STRING)
					.description("답장 읽음 상태")
					.attributes(constraints("UNREAD || READ")),
			fieldWithPath("reply.createdAt")
					.type(JsonFieldType.STRING)
					.description("생성일"),
			fieldWithPath("reply.updatedAt")
					.type(JsonFieldType.STRING)
					.description("수정일")
	);

}
