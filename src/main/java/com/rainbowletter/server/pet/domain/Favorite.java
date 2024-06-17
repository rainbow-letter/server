package com.rainbowletter.server.pet.domain;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.exception.RainbowLetterException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "favorite")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private int total;

	@NotNull
	private int dayIncreaseCount;

	@NotNull
	private boolean canIncrease;

	@NotNull
	private LocalDateTime lastIncreasedAt;

	public Favorite(final TimeHolder timeHolder) {
		this(null, 0, 0, true, timeHolder);
	}

	@Builder
	public Favorite(
			final Long id,
			final int total,
			final int dayIncreaseCount,
			final boolean canIncrease,
			final TimeHolder timeHolder
	) {
		this.id = id;
		this.total = total;
		this.dayIncreaseCount = dayIncreaseCount;
		this.canIncrease = canIncrease;
		this.lastIncreasedAt = timeHolder.currentTime();
	}

	public void increase(final TimeHolder timeHolder) {
		validateIncrease(dayIncreaseCount);
		this.total++;
		this.dayIncreaseCount++;
		this.canIncrease = canIncrease(dayIncreaseCount);
		this.lastIncreasedAt = timeHolder.currentTime();
	}

	private void validateIncrease(final int dayIncreaseCount) {
		if (!canIncrease(dayIncreaseCount)) {
			throw new RainbowLetterException("하루 최대 좋아요 수를 달성하였습니다.");
		}
	}

	private boolean canIncrease(final int dayIncreaseCount) {
		return dayIncreaseCount < 3;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof final Favorite favorite)) {
			return false;
		}
		return Objects.equals(id, favorite.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

}
