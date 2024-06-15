package com.rainbowletter.server.notification.infrastructure;

import com.rainbowletter.server.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationJpaRepository extends JpaRepository<Notification, Long> {

}
