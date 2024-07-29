package com.rainbowletter.server.temporary.domain;

import static com.rainbowletter.server.temporary.domain.TemporaryStatus.DELETE;
import static com.rainbowletter.server.temporary.domain.TemporaryStatus.SAVE;
import static lombok.AccessLevel.PROTECTED;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.application.port.UuidHolder;
import com.rainbowletter.server.common.domain.TimeEntity;
import com.rainbowletter.server.temporary.dto.TemporaryCreate;
import com.rainbowletter.server.temporary.dto.TemporaryUpdate;
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

@Getter
@Entity
@Table(name = "temporary")
@NoArgsConstructor(access = PROTECTED)
public class Temporary extends AbstractAggregateRoot<Temporary> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long userId;

	@NotNull
	private Long petId;

	@NotNull
	@JdbcTypeCode(SqlTypes.VARCHAR)
	private UUID sessionId;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String content;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TemporaryStatus status;

	@Embedded
	private TimeEntity timeEntity;

	public Temporary(
			final Long userId,
			final TemporaryCreate temporaryCreate,
			final UuidHolder uuidHolder,
			final TimeHolder timeHolder
	) {
		this(null, userId, temporaryCreate.petId(), uuidHolder, temporaryCreate.content(), SAVE, timeHolder);
	}

	@Builder
	public Temporary(
			final Long id,
			final Long userId,
			final Long petId,
			final UuidHolder uuidHolder,
			final String content,
			final TemporaryStatus status,
			final TimeHolder timeHolder
	) {
		this.id = id;
		this.userId = userId;
		this.petId = petId;
		this.sessionId = uuidHolder.generate();
		this.content = content;
		this.status = status;
		this.timeEntity = new TimeEntity(timeHolder);
	}

	public void update(final TemporaryUpdate temporaryUpdate) {
		this.content = temporaryUpdate.content();
	}

	public void changeSession(final UuidHolder uuidHolder) {
		this.sessionId = uuidHolder.generate();
	}

	public void delete() {
		this.status = DELETE;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof final Temporary temporary)) {
			return false;
		}
		return Objects.equals(id, temporary.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

}
