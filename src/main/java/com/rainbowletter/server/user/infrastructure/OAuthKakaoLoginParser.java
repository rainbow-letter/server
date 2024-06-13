package com.rainbowletter.server.user.infrastructure;

import com.rainbowletter.server.user.domain.OAuthProvider;
import java.util.Map;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class OAuthKakaoLoginParser implements OAuthLoginProviderParser {

	@Override
	public boolean support(final OAuthProvider provider) {
		return OAuthProvider.KAKAO.equals(provider);
	}

	@Override
	public String getUsername(final OAuth2User user) {
		return ((Map<?, ?>) user.getAttributes().get("kakao_account")).get("email").toString();
	}

	@Override
	public String getProviderId(final OAuth2User user) {
		return user.getAttributes().get("id").toString();
	}

}
