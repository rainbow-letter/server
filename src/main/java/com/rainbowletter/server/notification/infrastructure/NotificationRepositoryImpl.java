package com.rainbowletter.server.notification.infrastructure;

import com.rainbowletter.server.notification.application.port.NotificationRepository;
import com.rainbowletter.server.notification.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

	private final NotificationJpaRepository notificationJpaRepository;

	@Override
	public void save(final Notification notification) {
		notificationJpaRepository.save(notification);
	}

}
