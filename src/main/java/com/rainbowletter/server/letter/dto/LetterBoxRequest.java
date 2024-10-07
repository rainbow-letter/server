package com.rainbowletter.server.letter.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public record LetterBoxRequest(String email, Long petId, LocalDateTime startDate, LocalDateTime endDate) {

	public static LetterBoxRequest of(
			final String email,
			final Long petId,
			final LocalDate start,
			final LocalDate end
	) {
		LocalDateTime startDate = null;
		LocalDateTime endDate = null;

		if (Objects.nonNull(start) && Objects.nonNull(end)) {
			startDate = LocalDateTime.of(start, LocalTime.MIN);
			endDate = LocalDateTime.of(end, LocalTime.MAX).withNano(0);
		}
		return new LetterBoxRequest(email, petId, startDate, endDate);
	}

}
