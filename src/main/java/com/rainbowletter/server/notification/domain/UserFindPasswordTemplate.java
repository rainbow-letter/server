package com.rainbowletter.server.notification.domain;

import static com.rainbowletter.server.notification.domain.EmailTemplateType.FIND_PASSWORD;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.infrastructure.JwtTokenProvider;
import com.rainbowletter.server.common.property.ClientProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class UserFindPasswordTemplate extends AbstractEmailTemplate implements EmailTemplate {

	private static final int DEFAULT_VERIFY_EXPIRATION_MINUTE = 10;

	private final TimeHolder timeHolder;
	private final ClientProperty clientProperty;
	private final TemplateEngine templateEngine;
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public boolean support(final EmailTemplateType templateType) {
		return FIND_PASSWORD.equals(templateType);
	}

	@Override
	public String getContent(final String receiver, final Object... args) {
		validate(FIND_PASSWORD, 0, args);
		final var expire = timeHolder.currentTimeMillis() + timeHolder.minuteToMillis(DEFAULT_VERIFY_EXPIRATION_MINUTE);
		final var token = jwtTokenProvider.create(receiver, "VERIFY", expire);
		final var url = clientProperty.getBaseUrl() + "/users/password/reset?token=" + token;
		final Context context = new Context();
		context.setVariable("url", url);
		return templateEngine.process("reset-password", context);
	}

}
