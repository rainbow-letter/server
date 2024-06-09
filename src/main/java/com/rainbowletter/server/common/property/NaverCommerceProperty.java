package com.rainbowletter.server.common.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class NaverCommerceProperty {

	@Value("${naver.commerce.id}")
	private String id;

	@Value("${naver.commerce.secret}")
	private String secret;

}
