package com.rainbowletter.server.common.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AligoProperty {

	@Value("${aligo.access-key}")
	private String accessKey;

	@Value("${aligo.sender-key}")
	private String senderKey;

	@Value("${aligo.sender}")
	private String sender;

}
