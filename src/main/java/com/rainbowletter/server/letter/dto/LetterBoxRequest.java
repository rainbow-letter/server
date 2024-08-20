package com.rainbowletter.server.letter.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record LetterBoxRequest(String email, Long petId, LocalDateTime startDate, LocalDateTime endDate) {

	public static LetterBoxRequest of(
			final String email,
			final Long petId,
			final LocalDate start,
			final LocalDate end
	) {
		final LocalDateTime startDate = LocalDateTime.of(start, LocalTime.MIN);
		final LocalDateTime endDate = LocalDateTime.of(end, LocalTime.MAX).withNano(0);
		return new LetterBoxRequest(email, petId, startDate, endDate);
	}

}
