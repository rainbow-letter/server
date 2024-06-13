package com.rainbowletter.server.user.infrastructure;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.application.port.UuidHolder;
import com.rainbowletter.server.user.application.port.OAuthLoginHandler;
import com.rainbowletter.server.user.application.port.UserRepository;
import com.rainbowletter.server.user.domain.OAuthProvider;
import com.rainbowletter.server.user.domain.User;
import com.rainbowletter.server.user.dto.UserCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OAuthLoginHandlerImpl implements OAuthLoginHandler {

	private final UuidHolder uuidHolder;
	private final TimeHolder timeHolder;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final OAuthLoginProviderParserManager oAuthLoginProviderParserManager;

	@Override
	@Transactional
	public User process(final String registrationId, final OAuth2User user) {
		final OAuthProvider provider = OAuthProvider.get(registrationId);
		final String email = oAuthLoginProviderParserManager.getUsername(provider, user);
		final String providerId = oAuthLoginProviderParserManager.getProviderId(provider, user);

		if (userRepository.existsByEmail(email)) {
			return updateUser(email, provider, providerId);
		}
		return saveUser(email, provider, providerId);
	}

	private User updateUser(final String email, final OAuthProvider provider, final String providerId) {
		final User user = userRepository.findByEmailOrElseThrow(email);
		user.login(provider, providerId, timeHolder);
		return userRepository.save(user);
	}

	private User saveUser(final String email, final OAuthProvider provider, final String providerId) {
		final UserCreate userCreate = UserCreate.of(email, uuidHolder.generate().toString(), provider, providerId);
		final User user = new User(userCreate, passwordEncoder, timeHolder);
		return userRepository.save(user);
	}

}
