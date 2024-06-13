package com.rainbowletter.server.common.infrastructure;

import com.rainbowletter.server.common.exception.RainbowLetterException;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

	public static String getEmail() {
		final Authentication authentication = getAuthentication();
		return parseUsername(authentication);
	}

	public static String getRole() {
		final Authentication authentication = getAuthentication();
		return parseAuthority(authentication);
	}

	private static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static String parseUsername(final Authentication authentication) {
		if (Objects.isNull(authentication) || authentication.getPrincipal() instanceof String) {
			throw new RainbowLetterException("잘못된 사용자 인증 정보입니다.");
		}
		return authentication.getName();
	}

	public static String parseAuthority(final Authentication authentication) {
		return authentication.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.findAny()
				.orElseThrow(() -> new RainbowLetterException("사용자 권한 정보를 찾을 수 없습니다."));
	}

}
