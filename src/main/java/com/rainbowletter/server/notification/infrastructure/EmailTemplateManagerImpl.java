package com.rainbowletter.server.notification.infrastructure;

import com.rainbowletter.server.common.exception.RainbowLetterException;
import com.rainbowletter.server.notification.domain.EmailTemplate;
import com.rainbowletter.server.notification.domain.EmailTemplateType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailTemplateManagerImpl implements EmailTemplateManager {

	private final List<EmailTemplate> templates;

	@Override
	public String getTitle(final EmailTemplateType type, final String title) {
		final EmailTemplate template = findTemplate(type);
		return template.getTitle(title);
	}

	@Override
	public String getContent(final EmailTemplateType type, final String receiver, final Object... args) {
		final EmailTemplate template = findTemplate(type);
		return template.getContent(receiver, args);
	}

	private EmailTemplate findTemplate(final EmailTemplateType type) {
		return templates.stream()
				.filter(template -> template.support(type))
				.findAny()
				.orElseThrow(() -> new RainbowLetterException("이메일 템플릿(%s)이 존재하지 않습니다.", type));
	}

}
