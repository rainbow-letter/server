package com.rainbowletter.server.notification.domain;

public interface EmailTemplate {

	boolean support(EmailTemplateType templateType);

	String getContent(String receiver, Object... args);

	default String getTitle(final String title) {
		return "[무지개 편지] %s".formatted(title);
	}

}
