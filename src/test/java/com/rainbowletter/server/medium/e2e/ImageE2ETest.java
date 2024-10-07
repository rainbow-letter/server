package com.rainbowletter.server.medium.e2e;

import static com.rainbowletter.server.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_KEY;
import static com.rainbowletter.server.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_TYPE;
import static com.rainbowletter.server.medium.RestDocsUtils.getFilter;
import static com.rainbowletter.server.medium.RestDocsUtils.getSpecification;
import static com.rainbowletter.server.medium.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER;
import static com.rainbowletter.server.medium.snippet.ImageRequestSnippet.IMAGE_UPLOAD_HEADER;
import static com.rainbowletter.server.medium.snippet.ImageRequestSnippet.IMAGE_UPLOAD_MULTIPART;
import static com.rainbowletter.server.medium.snippet.ImageRequestSnippet.PATH_PARAM_OBJECT_KEY;
import static com.rainbowletter.server.medium.snippet.ImageResponseSnippet.IMAGE_UPLOAD_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import com.rainbowletter.server.image.domain.ImageFileManager;
import com.rainbowletter.server.image.dto.ImageUploadResponse;
import com.rainbowletter.server.medium.TestHelper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.io.File;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

class ImageE2ETest extends TestHelper {

	@MockBean
	private ImageFileManager imageFileManager;

	@Test
	void Should_UploadImage_When_ValidRequest() {
		// given
		final String token = userAccessToken;
		BDDMockito.given(imageFileManager.save(any(MultipartFile.class)))
				.willReturn("202401/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

		// when
		final ExtractableResponse<Response> response = upload(token);
		final ImageUploadResponse result = response.body().as(ImageUploadResponse.class);

		// then
		assertThat(response.statusCode()).isEqualTo(201);
		assertThat(result.objectKey()).isEqualTo("202401/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
	}

	private ExtractableResponse<Response> upload(final String token) {
		return RestAssured
				.given(getSpecification()).log().all()
				.header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
				.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
				.multiPart("file", new File("src/main/resources/static/images/logo.png"))
				.filter(getFilter().document(IMAGE_UPLOAD_HEADER, IMAGE_UPLOAD_MULTIPART, IMAGE_UPLOAD_RESPONSE))
				.when().post("/api/images")
				.then().log().all().extract();
	}

	@Test
	void Should_DeleteImage_When_Authenticated() {
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
				.filter(getFilter().document(AUTHORIZATION_HEADER, PATH_PARAM_OBJECT_KEY))
				.when().delete("/api/images/{dirName}/{fileName}", "202401", "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
				.then().log().all().extract();
	}

}
