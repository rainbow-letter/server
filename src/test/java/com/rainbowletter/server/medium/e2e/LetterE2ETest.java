package com.rainbowletter.server.medium.e2e;

import static com.rainbowletter.server.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_KEY;
import static com.rainbowletter.server.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_TYPE;
import static com.rainbowletter.server.medium.RestDocsUtils.getFilter;
import static com.rainbowletter.server.medium.RestDocsUtils.getSpecification;
import static com.rainbowletter.server.medium.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER;
import static com.rainbowletter.server.medium.snippet.LetterRequestSnippet.LETTER_CREATE_REQUEST;
import static com.rainbowletter.server.medium.snippet.LetterRequestSnippet.LETTER_PARAM_PET_ID;
import static com.rainbowletter.server.medium.snippet.LetterRequestSnippet.LETTER_PATH_VARIABLE_ID;
import static com.rainbowletter.server.medium.snippet.LetterResponseSnippet.LETTER_CREATE_RESPONSE_HEADER;
import static org.assertj.core.api.Assertions.assertThat;

import com.rainbowletter.server.letter.dto.LetterCreateRequest;
import com.rainbowletter.server.medium.TestHelper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:sql/user.sql", "classpath:sql/pet.sql", "classpath:sql/letter.sql"})
class LetterE2ETest extends TestHelper {

	@Test
	void Should_CreateLetter_When_ValidRequest() {
		// given
		final String token = userAccessToken;
		final var request = new LetterCreateRequest("편지 제목", "편지 내용", "202401/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

		// when
		final ExtractableResponse<Response> response = create(token, request);

		// then
		assertThat(response.statusCode()).isEqualTo(201);
	}

	private ExtractableResponse<Response> create(final String token, final LetterCreateRequest request) {
		return RestAssured
				.given(getSpecification()).log().all()
				.header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(request)
				.queryParam("pet", 1)
				.filter(getFilter().document(
						AUTHORIZATION_HEADER,
						LETTER_PARAM_PET_ID,
						LETTER_CREATE_REQUEST,
						LETTER_CREATE_RESPONSE_HEADER
				))
				.when().post("/api/letters")
				.then().log().all().extract();
	}

	@Test
	void Should_DeleteLetter_When_ValidRequest() {
		// given
		final String token = userAccessToken;

		// when
		final ExtractableResponse<Response> response = delete(token);

		// then
		assertThat(response.statusCode()).isEqualTo(200);
	}

	private ExtractableResponse<Response> delete(final String token) {
		return RestAssured
				.given(getSpecification()).log().all()
				.header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.filter(getFilter().document(AUTHORIZATION_HEADER, LETTER_PATH_VARIABLE_ID))
				.when().delete("/api/letters/{id}", 4)
				.then().log().all().extract();
	}

}
