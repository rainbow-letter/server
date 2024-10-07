package com.rainbowletter.server.notification.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainbowletter.server.common.property.AligoProperty;
import com.rainbowletter.server.common.property.EnvironmentProperty;
import com.rainbowletter.server.notification.application.port.NotificationSender;
import com.rainbowletter.server.notification.dto.AlimTalkSendRequest;
import com.rainbowletter.server.notification.dto.AlimTalkSendResponse;
import com.rainbowletter.server.notification.dto.MailSendRequest;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
public class NotificationSenderImpl implements NotificationSender {

	private final ObjectMapper objectMapper;
	private final JavaMailSender mailSender;
	private final AligoProperty aligoProperty;
	private final AlimTalkClient alimTalkClient;
	private final EnvironmentProperty environmentProperty;

	@Override
	public void sendMail(final MailSendRequest request) throws MessagingException {
		if (!environmentProperty.isActiveTest()) {
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

	@Override
	public AlimTalkSendResponse sendAlimTalk(final AlimTalkSendRequest request) throws IOException {
		final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		if (environmentProperty.isActiveTest()) {
			body.add("testMode", "Y");
		}
		if (request.useEmTitle()) {
			body.add("emtitle_1", request.title());
		}
		body.add("apikey", aligoProperty.getAccessKey());
		body.add("userid", "rainbowletter");
		body.add("senderkey", aligoProperty.getSenderKey());
		body.add("tpl_code", request.templateCode());
		body.add("sender", aligoProperty.getSender());
		body.add("receiver_1", request.receiver());
		body.add("subject_1", request.title());
		body.add("message_1", request.content());
		body.add("button_1", objectMapper.writeValueAsString(request.buttons()));
		body.add("failover", "Y");
		body.add("fsubject_1", request.failTitle());
		body.add("fmessage_1", request.failContent());
		return alimTalkClient.sendAlimTalk(body);
	}

}
