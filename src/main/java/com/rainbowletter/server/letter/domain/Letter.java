package com.rainbowletter.server.letter.domain;

import static lombok.AccessLevel.PROTECTED;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.application.port.UuidHolder;
import com.rainbowletter.server.common.domain.TimeEntity;
import com.rainbowletter.server.letter.dto.LetterCreate;
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
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.util.StringUtils;

@Getter
@Entity
@Table(name = "letter")
@NoArgsConstructor(access = PROTECTED)
public class Letter extends AbstractAggregateRoot<Letter> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long userId;

	@NotNull
	private Long petId;

	@NotNull
	@Column(length = 20)
	private String summary;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String content;

	@NotNull
	@JdbcTypeCode(SqlTypes.VARCHAR)
	private UUID shareLink;

	private String image;

	@NotNull
	@Enumerated(EnumType.STRING)
	private LetterStatus status;

	@Embedded
	private TimeEntity timeEntity;

	public Letter(
			final Long userId,
			final Long petId,
			final LetterCreate letterCreate,
			final UuidHolder uuidHolder,
			final TimeHolder timeHolder
	) {
		this(null, userId, petId, letterCreate, uuidHolder.generate(), timeHolder);
	}

	@Builder
	public Letter(
			final Long id,
			final Long userId,
			final Long petId,
			final LetterCreate letterCreate,
			final UUID shareLink,
			final TimeHolder timeHolder
	) {
		this.id = id;
		this.userId = userId;
		this.petId = petId;
		this.summary = letterCreate.summary();
		this.content = letterCreate.content();
		this.shareLink = shareLink;
		this.image = letterCreate.image();
		this.status = LetterStatus.REQUEST;
		this.timeEntity = new TimeEntity(timeHolder);
		registerEvent(new LetterCreateEvent(this));
	}

	public boolean hasImage() {
		return StringUtils.hasText(image);
	}

	public void delete() {
		registerEvent(new LetterDeleteEvent(this));
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof final Letter letter)) {
			return false;
		}
		return Objects.equals(id, letter.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

}
