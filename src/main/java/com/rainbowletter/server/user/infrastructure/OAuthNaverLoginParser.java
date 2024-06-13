package com.rainbowletter.server.user.infrastructure;

import com.rainbowletter.server.user.domain.OAuthProvider;
import java.util.Map;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class OAuthNaverLoginParser implements OAuthLoginProviderParser {

	@Override
	public boolean support(final OAuthProvider provider) {
		return OAuthProvider.NAVER.equals(provider);
	}

	@Override
	public String getUsername(final OAuth2User user) {
		return ((Map<?, ?>) user.getAttributes().get("response")).get("email").toString();
	}

	@Override
	public String getProviderId(final OAuth2User user) {
		return ((Map<?, ?>) user.getAttributes().get("response")).get("id").toString();
	}

}
