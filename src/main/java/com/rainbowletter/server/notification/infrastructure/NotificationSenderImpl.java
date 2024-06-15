package com.rainbowletter.server.notification.infrastructure;

import com.rainbowletter.server.common.property.EnvironmentProperty;
import com.rainbowletter.server.notification.application.port.NotificationSender;
import com.rainbowletter.server.notification.dto.MailSendRequest;
import jakarta.mail.MessagingException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationSenderImpl implements NotificationSender {

	private final JavaMailSender mailSender;
	private final EnvironmentProperty environmentProperty;

	@Override
	public void sendMail(final MailSendRequest request) throws MessagingException {
		if (environmentProperty.isActiveProduction()) {
			final var mimeMessage = mailSender.createMimeMessage();
			final var messageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
			messageHelper.setFrom("무지개 편지 <noreply@rainbowletter.co.kr>");
			messageHelper.setTo(request.receiver());
			messageHelper.setSubject(request.title());
			messageHelper.setText(request.content(), true);
			messageHelper.addInline("logo", new ClassPathResource("static/images/logo.png"));
			mailSender.send(mimeMessage);
		}
	}

}
