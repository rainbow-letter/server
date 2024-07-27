package com.rainbowletter.server.common.config.api;

import com.rainbowletter.server.chatgpt.infrastructure.ChatGptClient;
import com.rainbowletter.server.common.exception.RainbowLetterException;
import com.rainbowletter.server.common.property.ChatGptProperty;
import com.rainbowletter.server.notification.infrastructure.AlimTalkClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@RequiredArgsConstructor
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
public class ApiConfig {

	private static final String ALIGO_KAKAO_BASE_URL = "https://kakaoapi.aligo.in";
	private static final String OPEN_AI_BASE_URL = "https://api.openai.com";

	private final ChatGptProperty chatGptProperty;

	@Bean
	public AlimTalkClient alimTalkClient() {
		final RestClient restClient = RestClient.builder()
				.baseUrl(ALIGO_KAKAO_BASE_URL)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.defaultStatusHandler(HttpStatusCode::is5xxServerError, ((request, response) -> {
					throw new RainbowLetterException("알림톡 서버에 문제가 발생하였습니다.");
				}))
				.build();
		return HttpServiceProxyFactory
				.builderFor(RestClientAdapter.create(restClient))
				.build()
				.createClient(AlimTalkClient.class);
	}

	@Bean
	public ChatGptClient chatGptClient() {
		final RestClient restClient = RestClient.builder()
				.baseUrl(OPEN_AI_BASE_URL)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader("Authorization", "Bearer " + chatGptProperty.getToken())
				.defaultStatusHandler(HttpStatusCode::is5xxServerError, ((request, response) -> {
					throw new RainbowLetterException("OPEN AI 서버에 문제가 발생하였습니다.");
				}))
				.build();
		return HttpServiceProxyFactory
				.builderFor(RestClientAdapter.create(restClient))
				.build()
				.createClient(ChatGptClient.class);
	}

}
