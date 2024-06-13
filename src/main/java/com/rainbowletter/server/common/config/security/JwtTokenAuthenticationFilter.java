package com.rainbowletter.server.common.config.security;

import com.rainbowletter.server.common.dto.TokenInfo;
import com.rainbowletter.server.common.exception.RainbowLetterException;
import com.rainbowletter.server.common.infrastructure.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends GenericFilter {

	public static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	public static final String AUTHORIZATION_HEADER_TYPE = "Bearer";

	private final transient JwtTokenProvider jwtTokenProvider;

	@Override
	public void doFilter(
			final ServletRequest servletRequest,
			final ServletResponse servletResponse,
			final FilterChain filterChain
	) throws IOException, ServletException {
		final String token = parseTokenFromRequest(servletRequest);
		saveAuthentication(token);
		filterChain.doFilter(servletRequest, servletResponse);
	}

	private String parseTokenFromRequest(final ServletRequest servletRequest) {
		final var request = (HttpServletRequest) servletRequest;
		final var bearerToken = request.getHeader(AUTHORIZATION_HEADER_KEY);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AUTHORIZATION_HEADER_TYPE + " ")) {
			return bearerToken.substring(7);
		}
		return "INVALID_TOKEN";
	}

	private void saveAuthentication(final String token) {
		try {
			final TokenInfo decodedToken = jwtTokenProvider.parse(token);
			final Authentication authentication = buildAuthenticationFromTokenInfo(decodedToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (final RainbowLetterException exception) {
			SecurityContextHolder.getContext().setAuthentication(null);
		}
	}

	private Authentication buildAuthenticationFromTokenInfo(final TokenInfo tokenInfo) {
		final var authorities = List.of(new SimpleGrantedAuthority(tokenInfo.claimValue()));
		final var principal = new User(tokenInfo.subject(), "", authorities);
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}

}
