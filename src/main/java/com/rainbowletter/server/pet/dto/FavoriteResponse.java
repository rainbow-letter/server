package com.rainbowletter.server.pet.dto;

import com.rainbowletter.server.pet.domain.Favorite;
import java.time.LocalDateTime;

public record FavoriteResponse(
		Long id,
		int total,
		int dayIncreaseCount,
		boolean canIncrease,
		LocalDateTime lastIncreasedAt
) {

	public static FavoriteResponse from(final Favorite favorite) {
		return new FavoriteResponse(
				favorite.getId(),
				favorite.getTotal(),
				favorite.getDayIncreaseCount(),
				favorite.isCanIncrease(),
				favorite.getLastIncreasedAt()
		);
	}

}
