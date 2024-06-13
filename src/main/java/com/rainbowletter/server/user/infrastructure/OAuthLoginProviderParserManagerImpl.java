package com.rainbowletter.server.user.infrastructure;

import com.rainbowletter.server.common.exception.RainbowLetterException;
import com.rainbowletter.server.user.domain.OAuthProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthLoginProviderParserManagerImpl implements OAuthLoginProviderParserManager {

	private final List<OAuthLoginProviderParser> parsers;

	@Override
	public String getUsername(final OAuthProvider provider, final OAuth2User user) {
		final OAuthLoginProviderParser oAuthLoginProviderParser = findProviderParser(provider);
		return oAuthLoginProviderParser.getUsername(user);
	}

	@Override
	public String getProviderId(final OAuthProvider provider, final OAuth2User user) {
		final OAuthLoginProviderParser oAuthLoginProviderParser = findProviderParser(provider);
		return oAuthLoginProviderParser.getProviderId(user);
	}

	private OAuthLoginProviderParser findProviderParser(final OAuthProvider provider) {
		return parsers.stream()
				.filter(parser -> parser.support(provider))
				.findAny()
				.orElseThrow(() -> new RainbowLetterException("현재 지원하지 않는 로그인 타입(%s) 입니다.", provider));
	}

}
