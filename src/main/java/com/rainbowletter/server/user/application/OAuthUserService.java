package com.rainbowletter.server.user.application;

import com.rainbowletter.server.user.application.port.OAuthLoginHandler;
import com.rainbowletter.server.user.domain.OAuthUserDetails;
import com.rainbowletter.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthUserService extends DefaultOAuth2UserService {

	private final OAuthLoginHandler oAuthLoginHandler;

	@Override
	public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		final OAuth2User oAuthUser = super.loadUser(userRequest);
		final String registrationId = userRequest.getClientRegistration().getRegistrationId();
		final User user = oAuthLoginHandler.process(registrationId, oAuthUser);
		return new OAuthUserDetails(user, oAuthUser.getAttributes());
	}

}
