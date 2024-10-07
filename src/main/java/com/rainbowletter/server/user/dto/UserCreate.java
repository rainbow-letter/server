package com.rainbowletter.server.user.dto;

import static com.rainbowletter.server.user.domain.OAuthProvider.NONE;
import static com.rainbowletter.server.user.domain.UserRole.ROLE_USER;
import static com.rainbowletter.server.user.domain.UserStatus.ACTIVE;

import com.rainbowletter.server.user.domain.OAuthProvider;
import com.rainbowletter.server.user.domain.UserRole;
import com.rainbowletter.server.user.domain.UserStatus;

public record UserCreate(
		String email,
		String password,
		String phoneNumber,
		UserRole role,
		UserStatus status,
		OAuthProvider provider,
		String providerId
) {

	public static UserCreate of(final String email, final String password) {
		return new UserCreate(email, password, null, ROLE_USER, ACTIVE, NONE, NONE.name());
	}

	public static UserCreate of(
			final String email,
			final String password,
			final OAuthProvider provider,
			final String providerId
	) {
		return new UserCreate(email, password, null, ROLE_USER, ACTIVE, provider, providerId);
	}

}
