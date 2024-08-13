package com.rainbowletter.server.common.property;

import java.util.Objects;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class EnvironmentProperty {

	@Value("${spring.profiles.active}")
	private String activeProfile;

	public boolean isActiveTest() {
		return Objects.nonNull(activeProfile) && activeProfile.contains("test");
	}

}
