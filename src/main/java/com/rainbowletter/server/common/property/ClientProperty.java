package com.rainbowletter.server.common.property;

import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ClientProperty {

	@Value("${client.url}")
	private List<String> urls;

	public String getBaseUrl() {
		return urls.get(0);
	}

}
