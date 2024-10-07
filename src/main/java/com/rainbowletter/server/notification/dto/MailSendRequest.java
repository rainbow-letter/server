package com.rainbowletter.server.notification.dto;

public record MailSendRequest(String receiver, String title, String content) implements NotificationSendRequest {

}
