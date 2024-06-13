package com.rainbowletter.server.user.infrastructure;

import com.rainbowletter.server.user.domain.OAuthProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuthLoginProviderParser {

	boolean support(OAuthProvider provider);

	String getUsername(OAuth2User user);

	String getProviderId(OAuth2User user);

}
