package com.rainbowletter.server.common.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ChatGptProperty {

	@Value("${chatgpt.token}")
	private String token;

}
