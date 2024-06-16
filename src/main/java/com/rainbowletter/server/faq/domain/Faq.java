package com.rainbowletter.server.faq.domain;

import static lombok.AccessLevel.PROTECTED;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.domain.TimeEntity;
import com.rainbowletter.server.faq.dto.FaqCreate;
import com.rainbowletter.server.faq.dto.FaqUpdate;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.domain.AbstractAggregateRoot;

@Getter
@Entity
@DynamicInsert
@Table(name = "faq")
@NoArgsConstructor(access = PROTECTED)
public class Faq extends AbstractAggregateRoot<Faq> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(length = 30)
	private String summary;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String detail;

	@NotNull
	private boolean visibility;

	private Long sequence;

	@Embedded
	private TimeEntity timeEntity;

	public Faq(final FaqCreate faqCreate, final TimeHolder timeHolder) {
		this(null, faqCreate.summary(), faqCreate.detail(), true, null, timeHolder);
	}

	@Builder
	public Faq(
			final Long id,
			final String summary,
			final String detail,
			final boolean visibility,
			final Long sequence,
			final TimeHolder timeHolder
	) {
		this.id = id;
		this.summary = summary;
		this.detail = detail;
		this.visibility = visibility;
		this.sequence = sequence;
		this.timeEntity = new TimeEntity(timeHolder);
	}

	@PostPersist
	private void initSequence() {
		sequence = id;
	}

	public void update(final FaqUpdate faqUpdate) {
		this.summary = faqUpdate.summary();
		this.detail = faqUpdate.detail();
	}

	public void changeVisibility() {
		this.visibility = !visibility;
	}

	public void switchSequence(final Faq targetFaq) {
		final Long tempSequence = this.sequence;
		changeSequence(targetFaq.getSequence());
		targetFaq.changeSequence(tempSequence);
	}

	private void changeSequence(final Long sequence) {
		this.sequence = sequence;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof final Faq faq)) {
			return false;
		}
		return Objects.equals(id, faq.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

}
