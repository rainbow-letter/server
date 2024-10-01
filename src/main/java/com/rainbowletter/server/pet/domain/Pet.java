package com.rainbowletter.server.pet.domain;

import static lombok.AccessLevel.PROTECTED;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.domain.EventLog;
import com.rainbowletter.server.common.domain.EventLogger;
import com.rainbowletter.server.common.domain.TimeEntity;
import com.rainbowletter.server.pet.dto.PetCreate;
import com.rainbowletter.server.pet.dto.PetUpdate;
import com.rainbowletter.server.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.util.StringUtils;

@Getter
@Entity
@Table(name = "pet")
@NoArgsConstructor(access = PROTECTED)
public class Pet extends AbstractAggregateRoot<Pet> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long userId;

	@NotNull
	private String name;

	@NotNull
	private String species;

	@NotNull
	private String owner;

	@Convert(converter = PetPersonalitiesConverter.class)
	private List<String> personalities = new ArrayList<>();

	private LocalDate deathAnniversary;

	private String image;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "favorite_id", referencedColumnName = "id", nullable = false)
	private Favorite favorite;

	@Embedded
	private TimeEntity timeEntity;

	public Pet(final User user, final PetCreate petCreate, final TimeHolder timeHolder) {
		this(null, user.getId(), petCreate, timeHolder);
	}

	@Builder
	public Pet(
			final Long id,
			final Long userId,
			final PetCreate petCreate,
			final TimeHolder timeHolder
	) {
		this.id = id;
		this.userId = userId;
		this.name = petCreate.name();
		this.species = petCreate.species();
		this.owner = petCreate.owner();
		this.personalities.addAll(petCreate.personalities());
		this.deathAnniversary = petCreate.deathAnniversary();
		this.image = petCreate.image();
		this.favorite = new Favorite(timeHolder);
		this.timeEntity = new TimeEntity(timeHolder);
	}

	public void update(final PetUpdate petUpdate) {
		this.name = petUpdate.name();
		this.species = petUpdate.species();
		this.owner = petUpdate.owner();
		this.personalities = petUpdate.personalities();
		this.deathAnniversary = petUpdate.deathAnniversary();
		this.image = petUpdate.image();
	}

	public boolean hasImage() {
		return StringUtils.hasText(image);
	}

	public void increaseFavorite(final TimeHolder timeHolder) {
		favorite.increase(timeHolder);
	}

	public void delete() {
		registerEvent(new PetDeleteEvent(this));
	}

	public void delete(final EventLogger eventLogger, final TimeHolder timeHolder) {
		final EventLog eventLog = EventLog.success(id, userId, "PET", "DELETE", "", timeHolder);
		eventLogger.log(eventLog);
		registerEvent(new PetDeleteEvent(this));
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof final Pet pet)) {
			return false;
		}
		return Objects.equals(id, pet.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

}
