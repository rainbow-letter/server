package com.rainbowletter.server.user.dto;

import com.rainbowletter.server.user.domain.OAuthProvider;
import com.rainbowletter.server.user.domain.User;
import com.rainbowletter.server.user.domain.UserRole;
import java.time.LocalDateTime;

public record UserInformationResponse(
		Long id,
		String email,
		String phoneNumber,
		UserRole role,
		OAuthProvider provider,
		LocalDateTime lastLoggedIn,
		LocalDateTime lastChangedPassword,
		LocalDateTime createdAt
) {

	public static UserInformationResponse from(final User user) {
		return new UserInformationResponse(
				user.getId(),
				user.getEmail(),
				user.getPhoneNumber(),
				user.getRole(),
				user.getProvider(),
				user.getLastLoggedIn(),
				user.getLastChangedPassword(),
				user.getTimeEntity().getCreatedAt()
		);
	}

}
