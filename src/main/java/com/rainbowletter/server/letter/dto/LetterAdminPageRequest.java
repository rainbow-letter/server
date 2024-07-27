package com.rainbowletter.server.letter.dto;

import com.rainbowletter.server.reply.domain.ReplyStatus;
import jakarta.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.data.domain.Pageable;

public record LetterAdminPageRequest(
		LocalDateTime startDate,

		LocalDateTime endDate,

		@Nullable
		ReplyStatus status,

		@Nullable
		String email,

		@Nullable
		Boolean inspect,

		Pageable pageable
) {

	public static LetterAdminPageRequest of(
			final LocalDate startDate,
			final LocalDate endDate,
			final ReplyStatus status,
			final String email,
			final Boolean inspect,
			final Pageable pageable
	) {
		final LocalDateTime start = LocalDateTime.of(startDate, LocalTime.MIN);
		final LocalDateTime end = LocalDateTime.of(endDate, LocalTime.MAX).withNano(0);
		return new LetterAdminPageRequest(start, end, status, email, inspect, pageable);
	}

}
