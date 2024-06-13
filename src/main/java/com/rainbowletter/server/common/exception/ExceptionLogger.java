package com.rainbowletter.server.common.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionLogger {

	private static final String MESSAGE_FORMAT = "[%s] '%s' %s %s";
	private static final String INCLUDE_FIELD_MESSAGE_FORMAT = "[%s] '%s' %s %s - [%s]";

	public static void warn(final HttpStatus status, final String path, final String message) {
		log.warn(MESSAGE_FORMAT.formatted(status.name(), path, status.value(), message));
	}

	public static void warn(
			final HttpStatus status,
			final String path,
			final String message,
			final Object field
	) {
		log.warn(INCLUDE_FIELD_MESSAGE_FORMAT.formatted(status.name(), path, status.value(), message, field));
	}

	public static void error(
			final HttpStatus status,
			final String path,
			final String message,
			final Exception exception
	) {
		log.error(MESSAGE_FORMAT.formatted(status.name(), path, status.value(), message), exception);
	}

}
