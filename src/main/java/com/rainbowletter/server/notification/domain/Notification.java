package com.rainbowletter.server.notification.domain;

import static lombok.AccessLevel.PROTECTED;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.domain.TimeEntity;
import com.rainbowletter.server.notification.dto.AlimTalkSendResponse;
import com.rainbowletter.server.notification.dto.NotificationSendRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

@Getter
@Entity
@Table(name = "notification")
@NoArgsConstructor(access = PROTECTED)
public class Notification extends AbstractAggregateRoot<Notification> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(length = 100)
	private String title;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String content;

	@NotNull
	@Column(length = 50)
	private String receiver;

	@NotNull
	@Column(length = 50)
	private String sender;

	@Enumerated(EnumType.STRING)
	private NotificationType type;

	@NotNull
	private int code;

	@NotNull
	private String statusMessage;

	@Embedded
	private TimeEntity timeEntity;

	public Notification(
			final NotificationSendRequest request,
			final String sender,
			final NotificationType type,
			final TimeHolder timeHolder
	) {
		this(null, request, sender, type, 0, "", timeHolder);
	}

	public Notification(
			final NotificationSendRequest request,
			final String sender,
			final NotificationType type,
			final AlimTalkSendResponse response,
			final TimeHolder timeHolder
	) {
		this(null, request, sender, type, response.result_code(), response.message(), timeHolder);
	}

	@Builder
	public Notification(
			final Long id,
			final NotificationSendRequest request,
			final String sender,
			final NotificationType type,
			final int code,
			final String statusMessage,
			final TimeHolder timeHolder
	) {
		this.id = id;
		this.title = request.title();
		this.content = request.content();
		this.receiver = request.receiver();
		this.sender = sender;
		this.type = type;
		this.code = code;
		this.statusMessage = statusMessage;
		this.timeEntity = new TimeEntity(timeHolder);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof final Notification that)) {
			return false;
		}
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

}
