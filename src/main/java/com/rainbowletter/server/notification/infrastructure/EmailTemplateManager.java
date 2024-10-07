package com.rainbowletter.server.notification.infrastructure;

import com.rainbowletter.server.notification.domain.EmailTemplateType;

public interface EmailTemplateManager {

	String getTitle(EmailTemplateType type, String title);

	String getContent(EmailTemplateType type, String receiver, Object... args);

}
