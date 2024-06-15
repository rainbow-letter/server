package com.rainbowletter.server.notification.application.port;

import com.rainbowletter.server.notification.domain.Notification;

public interface NotificationRepository {

	void save(Notification notification);

}
