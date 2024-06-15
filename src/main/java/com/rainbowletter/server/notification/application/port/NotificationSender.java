package com.rainbowletter.server.notification.application.port;

import com.rainbowletter.server.notification.dto.AlimTalkSendRequest;
import com.rainbowletter.server.notification.dto.AlimTalkSendResponse;
import com.rainbowletter.server.notification.dto.MailSendRequest;
import jakarta.mail.MessagingException;
import java.io.IOException;

public interface NotificationSender {

	void sendMail(MailSendRequest request) throws MessagingException;

	AlimTalkSendResponse sendAlimTalk(AlimTalkSendRequest request) throws IOException;

}
