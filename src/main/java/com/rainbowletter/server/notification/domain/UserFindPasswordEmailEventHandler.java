package com.rainbowletter.server.notification.domain;

import static com.rainbowletter.server.notification.domain.EmailTemplateType.FIND_PASSWORD;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.domain.EventLog;
import com.rainbowletter.server.common.domain.EventLogger;
import com.rainbowletter.server.notification.application.port.NotificationRepository;
import com.rainbowletter.server.notification.application.port.NotificationSender;
import com.rainbowletter.server.notification.dto.MailSendRequest;
import com.rainbowletter.server.notification.infrastructure.EmailTemplateManager;
import com.rainbowletter.server.user.domain.User;
import com.rainbowletter.server.user.domain.UserFindPasswordEmailEvent;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserFindPasswordEmailEventHandler {

	private final TimeHolder timeHolder;
	private final NotificationSender notificationSender;
	private final EmailTemplateManager emailTemplateManager;
	private final NotificationRepository notificationRepository;
	private final EventLogger eventLogger;

	@Async
	@EventListener
	@Transactional
	public void handle(final UserFindPasswordEmailEvent event) {
		final var receiver = event.user().getEmail();
		final var title = emailTemplateManager.getTitle(FIND_PASSWORD, "비밀번호를 다시 설정해주세요!");
		final var content = emailTemplateManager.getContent(FIND_PASSWORD, receiver);
		final MailSendRequest request = new MailSendRequest(receiver, title, content);

		try {
			notificationSender.sendMail(request);
			final Notification notification = new Notification(request, "SERVER", NotificationType.MAIL, timeHolder);
			notificationRepository.save(notification);
		} catch (final MessagingException exception) {
			final EventLog eventLog = createFailLog("비밀번호 찾기 메일 발송에 실패하였습니다. [%s]".formatted(receiver), event.user());
			eventLogger.log(eventLog);
		}
	}

	private EventLog createFailLog(final String message, final User user) {
		return EventLog.fail(
				user.getId(),
				user.getId(),
				"USER",
				"NOTIFICATION",
				message,
				timeHolder
		);
	}

}
