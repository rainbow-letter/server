package com.rainbowletter.server.common.domain;

import static lombok.AccessLevel.PROTECTED;

import com.rainbowletter.server.common.application.port.TimeHolder;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

@Getter
@Entity
@Table(name = "log_event")
@NoArgsConstructor(access = PROTECTED)
public class EventLog extends AbstractAggregateRoot<EventLog> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long resource;

	private Long userId;

	@NotNull
	private String category;

	@NotNull
	private String event;

	@NotNull
	private String message;

	@NotNull
	@Enumerated(value = EnumType.STRING)
	private EventLogStatus status;

	@Embedded
	private TimeEntity timeEntity;

	private EventLog(
			final Long resource,
			final Long userId,
			final String category,
			final String event,
			final String message,
			final EventLogStatus status,
			final TimeHolder timeHolder
	) {
		this.id = null;
		this.resource = resource;
		this.userId = userId;
		this.category = category;
		this.event = event;
		this.message = message;
		this.status = status;
		this.timeEntity = new TimeEntity(timeHolder);
	}

	public static EventLog success(
			final Long resource,
			final Long userId,
			final String category,
			final String event,
			final String message,
			final TimeHolder timeHolder
	) {
		return new EventLog(
				resource,
				userId,
				category,
				event,
				message,
				EventLogStatus.SUCCESS,
				timeHolder
		);
	}

	public static EventLog fail(
			final Long resource,
			final Long userId,
			final String category,
			final String event,
			final String message,
			final TimeHolder timeHolder
	) {
		return new EventLog(
				resource,
				userId,
				category,
				event,
				message,
				EventLogStatus.FAIL,
				timeHolder
		);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof final EventLog eventLog)) {
			return false;
		}
		return Objects.equals(id, eventLog.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

}
