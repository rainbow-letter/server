package com.rainbowletter.server.common.config.log;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FormatType {
	DDL(List.of("create", "alter", "comment")),
	;

	private final List<String> commands;

	public static boolean isDDL(final String sql) {
		return FormatType.DDL
				.commands
				.stream()
				.anyMatch(sql::startsWith);
	}

}
