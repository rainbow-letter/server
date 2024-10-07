package com.rainbowletter.server.user.infrastructure;

import com.rainbowletter.server.user.domain.OAuthProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuthLoginProviderParserManager {

	String getUsername(OAuthProvider provider, OAuth2User user);

	String getProviderId(OAuthProvider provider, OAuth2User user);

}
