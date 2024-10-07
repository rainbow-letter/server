package com.rainbowletter.server.user.application.port;

import com.rainbowletter.server.user.domain.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuthLoginHandler {

	User process(String registrationId, OAuth2User user);

}
