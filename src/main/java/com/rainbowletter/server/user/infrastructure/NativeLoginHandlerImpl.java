package com.rainbowletter.server.user.infrastructure;

import com.rainbowletter.server.common.dto.TokenResponse;
import com.rainbowletter.server.common.infrastructure.JwtTokenProvider;
import com.rainbowletter.server.common.infrastructure.SecurityUtils;
import com.rainbowletter.server.user.application.port.NativeLoginHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NativeLoginHandlerImpl implements NativeLoginHandler {

	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManagerBuilder authenticationBuilder;

	@Override
	public TokenResponse process(final String email, final String password) {
		final var authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
		final Authentication authentication = authenticationBuilder.getObject().authenticate(authenticationToken);
		final String username = SecurityUtils.parseUsername(authentication);
		final String role = SecurityUtils.parseAuthority(authentication);
		final String accessToken = jwtTokenProvider.create(username, role);
		return new TokenResponse(accessToken);
	}

}
