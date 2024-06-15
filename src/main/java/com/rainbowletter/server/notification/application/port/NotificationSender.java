package com.rainbowletter.server.notification.application.port;

import com.rainbowletter.server.notification.dto.MailSendRequest;
import jakarta.mail.MessagingException;

public interface NotificationSender {

	void sendMail(MailSendRequest request) throws MessagingException;

}
