package com.rainbowletter.server.notification.domain;


import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.domain.EventLog;
import com.rainbowletter.server.common.domain.EventLogger;
import com.rainbowletter.server.common.property.ClientProperty;
import com.rainbowletter.server.letter.application.port.LetterRepository;
import com.rainbowletter.server.letter.domain.Letter;
import com.rainbowletter.server.notification.application.port.NotificationRepository;
import com.rainbowletter.server.notification.application.port.NotificationSender;
import com.rainbowletter.server.notification.dto.AlimTalkSendRequest;
import com.rainbowletter.server.notification.dto.AlimTalkSendRequest.AlimTalkSendButtonRequest;
import com.rainbowletter.server.notification.dto.MailSendRequest;
import com.rainbowletter.server.notification.infrastructure.EmailTemplateManager;
import com.rainbowletter.server.pet.application.port.PetRepository;
import com.rainbowletter.server.pet.domain.Pet;
import com.rainbowletter.server.reply.domain.ReplySubmitEvent;
import com.rainbowletter.server.user.application.port.UserRepository;
import com.rainbowletter.server.user.domain.User;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Profile("!test")
@Component
@RequiredArgsConstructor
public class NotificationReplySubmitEventHandler {

	private final TimeHolder timeHolder;
	private final ClientProperty clientProperty;
	private final UserRepository userRepository;
	private final PetRepository petRepository;
	private final LetterRepository letterRepository;
	private final NotificationSender notificationSender;
	private final EmailTemplateManager emailTemplateManager;
	private final NotificationRepository notificationRepository;
	private final EventLogger eventLogger;

	@Async
	@EventListener
	@Transactional
	public void handle(final ReplySubmitEvent event) {
		final Letter letter = letterRepository.findByIdOrElseThrow(event.reply().getLetterId());
		final Pet pet = petRepository.findByIdAndUserIdOrElseThrow(letter.getPetId(), letter.getUserId());
		final User user = userRepository.findByIdOrElseThrow(letter.getUserId());
		sendNotificationMail(user, pet, letter);
		sendNotificationAlimTalk(user, pet, letter);
	}

	private void sendNotificationMail(final User user, final Pet pet, final Letter letter) {
		final var title = emailTemplateManager.getTitle(EmailTemplateType.REPLY, pet.getName() + "에게 편지가 도착했어요!");
		final var content = emailTemplateManager.getContent(
				EmailTemplateType.REPLY, user.getEmail(), "/share/" + letter.getShareLink() + "?utm_source=replycheck");
		final MailSendRequest request = new MailSendRequest(user.getEmail(), title, content);

		try {
			notificationSender.sendMail(request);
			final var notification = new Notification(request, "SERVER", NotificationType.MAIL, timeHolder);
			notificationRepository.save(notification);
		} catch (final MessagingException exception) {
			final EventLog eventLog = createFailLog(
					"편지 답장 도착 메일 발송에 실패하였습니다. [%s]".formatted(user.getEmail()),
					user,
					letter
			);
			eventLogger.log(eventLog);
		}
	}

	private void sendNotificationAlimTalk(final User user, final Pet pet, final Letter letter) {
		if (!StringUtils.hasText(user.getPhoneNumber())) {
			return;
		}

		final var petName = pet.getName();
		final var letterCreatedAt = letter.getTimeEntity()
				.getCreatedAt()
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd일"));
		final var shareLinkUrl =
				clientProperty.getBaseUrl() + "/share/" + letter.getShareLink() + "?utm_source=replycheck";

		final AlimTalkSendRequest request = AlimTalkSendRequest.builder()
				.receiver(user.getPhoneNumber())
				.templateCode(AlimTalkTemplateImpl.REPLY.getCode())
				.title(AlimTalkTemplateImpl.REPLY.subject())
				.content(AlimTalkTemplateImpl.REPLY.content(petName, pet.getOwner(), letterCreatedAt, petName))
				.failTitle(AlimTalkTemplateImpl.REPLY.failSubject())
				.failContent(AlimTalkTemplateImpl.REPLY.failContent(letterCreatedAt, petName))
				.buttons(new AlimTalkSendButtonRequest(AlimTalkTemplateImpl.REPLY.buttons(shareLinkUrl)))
				.useEmTitle(false)
				.build();
		try {
			notificationSender.sendAlimTalk(request);
			final var notification = new Notification(request, "SERVER", NotificationType.ALIM_TALK, timeHolder);
			notificationRepository.save(notification);
		} catch (final IOException exception) {
			final EventLog eventLog = createFailLog(
					"편지 답장 도착 알림톡 발송에 실패하였습니다. [%s]".formatted(user.getPhoneNumber()),
					user,
					letter
			);
			eventLogger.log(eventLog);
		}
	}

	private EventLog createFailLog(final String message, final User user, final Letter letter) {
		return EventLog.fail(
				letter.getId(),
				user.getId(),
				"LETTER",
				"NOTIFICATION",
				message,
				timeHolder
		);
	}

}
