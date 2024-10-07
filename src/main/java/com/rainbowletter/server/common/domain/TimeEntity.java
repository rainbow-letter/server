package com.rainbowletter.server.common.domain;

import com.rainbowletter.server.common.application.port.TimeHolder;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeEntity {

	@NotNull
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@NotNull
	private LocalDateTime updatedAt;

	public TimeEntity(final TimeHolder timeHolder) {
		final LocalDateTime currentTime = timeHolder.currentTime();
		this.createdAt = currentTime;
		this.updatedAt = currentTime;
	}

	@PrePersist
	public void prePersist() {
		final LocalDateTime currentTime = LocalDateTime.now();
		createdAt = currentTime;
		updatedAt = currentTime;
	}

	@PreUpdate
	public void preUpdate() {
		updatedAt = LocalDateTime.now();
	}

}
