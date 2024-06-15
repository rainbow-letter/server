package com.rainbowletter.server.notification.domain;

import static com.rainbowletter.server.notification.domain.EmailTemplateType.REPLY;

import com.rainbowletter.server.common.property.ClientProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class LetterReceiveReplyTemplate extends AbstractEmailTemplate implements EmailTemplate {

	private final ClientProperty clientProperty;
	private final TemplateEngine templateEngine;

	@Override
	public boolean support(final EmailTemplateType templateType) {
		return REPLY.equals(templateType);
	}

	@Override
	public String getContent(final String receiver, final Object... args) {
		validate(REPLY, 1, args);
		final var url = clientProperty.getBaseUrl() + args[0];
		final Context context = new Context();
		context.setVariable("url", url);
		return templateEngine.process("receive-reply", context);
	}

}
