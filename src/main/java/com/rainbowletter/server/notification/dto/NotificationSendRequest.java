package com.rainbowletter.server.notification.dto;

public interface NotificationSendRequest {

	String receiver();

	String title();

	String content();

}
