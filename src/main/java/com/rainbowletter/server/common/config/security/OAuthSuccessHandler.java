package com.rainbowletter.server.common.config.security;

import com.rainbowletter.server.common.infrastructure.JwtTokenProvider;
import com.rainbowletter.server.common.infrastructure.SecurityUtils;
import com.rainbowletter.server.common.property.ClientProperty;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final ClientProperty clientProperty;
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void onAuthenticationSuccess(
			final HttpServletRequest request,
			final HttpServletResponse response,
			final Authentication authentication
	) throws IOException {
		final String username = SecurityUtils.parseUsername(authentication);
		final String role = SecurityUtils.parseAuthority(authentication);
		final String token = jwtTokenProvider.create(username, role);
		final String redirectUrl = clientProperty.getBaseUrl() + "/oauth/success?token=" + token;
		getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}

}
