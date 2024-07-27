package com.rainbowletter.server.common.infrastructure;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.dto.TokenInfo;
import com.rainbowletter.server.common.exception.RainbowLetterException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

	private static final String CLAIM_KEY_ROLES = "roles";
	private static final int DEFAULT_TOKEN_EXPIRATION_DAY = 30;

	private final SecretKey key;
	private final TimeHolder timeHolder;

	public JwtTokenProvider(@Value("${jwt.secret}") final String secretKey, final TimeHolder timeHolder) {
		final byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.timeHolder = timeHolder;
	}

	public String create(final String username, final String role) {
		final long expiration = timeHolder.currentTimeMillis() + timeHolder.dayToMillis(DEFAULT_TOKEN_EXPIRATION_DAY);
		return create(username, role, expiration);
	}

	public String create(final String username, final String role, final long expiration) {
		return Jwts.builder()
				.subject(username)
				.claim(CLAIM_KEY_ROLES, role)
				.expiration(new Date(expiration))
				.signWith(key)
				.compact();
	}

	public TokenInfo parse(final String token) {
		final Claims claims = decode(token);
		return TokenInfo.builder()
				.subject(claims.getSubject())
				.claimKey(CLAIM_KEY_ROLES)
				.claimValue(claims.get(CLAIM_KEY_ROLES).toString())
				.expiration(claims.getExpiration())
				.build();
	}

	private Claims decode(final String token) {
		try {
			return Jwts.parser()
					.verifyWith(key)
					.build()
					.parseSignedClaims(token)
					.getPayload();
		} catch (final IllegalArgumentException | JwtException exception) {
			throw new RainbowLetterException("유효하지 않은 토큰입니다.", exception);
		}
	}

}
