package com.rainbowletter.server.reply.domain;

import static lombok.AccessLevel.PROTECTED;

import com.rainbowletter.server.chatgpt.domain.ChatGptPromptType;
import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.domain.TimeEntity;
import com.rainbowletter.server.reply.dto.ReplyUpdate;
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
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

@Getter
@Entity
@Table(name = "reply")
@NoArgsConstructor(access = PROTECTED)
public class Reply extends AbstractAggregateRoot<Reply> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long petId;

	@NotNull
	private Long letterId;

	@NotNull
	@Column(length = 20)
	private String summary;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String content;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String gptContent;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ChatGptPromptType promptType;

	@NotNull
	private boolean inspection;

	private LocalDateTime inspectionTime;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ReplyStatus status;

	private LocalDateTime submitTime;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ReplyReadStatus readStatus;

	@Embedded
	private TimeEntity timeEntity;

	public Reply(
			final Long petId,
			final Long letterId,
			final String content,
			final ChatGptPromptType promptType,
			final TimeHolder timeHolder
	) {
		this(null, petId, letterId, content, promptType, timeHolder);
	}

	@Builder
	public Reply(
			final Long id,
			final Long petId,
			final Long letterId,
			final String gptContent,
			final ChatGptPromptType promptType,
			final TimeHolder timeHolder
	) {
		this.id = id;
		this.petId = petId;
		this.letterId = letterId;
		this.summary = gptContent.length() > 20 ? gptContent.substring(0, 20) : gptContent;
		this.content = gptContent;
		this.gptContent = gptContent;
		this.promptType = promptType;
		this.inspection = false;
		this.status = ReplyStatus.CHAT_GPT;
		this.readStatus = ReplyReadStatus.UNREAD;
		this.timeEntity = new TimeEntity(timeHolder);
	}

	public void inspect(final ReplyValidator replyValidator, final TimeHolder timeHolder) {
		replyValidator.validateInspect(this);
		this.inspection = !this.inspection;
		this.inspectionTime = timeHolder.currentTime();
	}

	public void submit(final ReplyValidator replyValidator, final TimeHolder timeHolder) {
		replyValidator.validateSubmit(this);
		this.status = ReplyStatus.REPLY;
		this.submitTime = timeHolder.currentTime();
		registerEvent(new ReplySubmitEvent(this));
	}

	public void read(final ReplyValidator replyValidator) {
		replyValidator.validateRead(this);
		this.readStatus = ReplyReadStatus.READ;
	}

	public boolean isSubmitted() {
		return this.status == ReplyStatus.REPLY;
	}

	public void update(final ReplyUpdate replyUpdate) {
		this.summary = replyUpdate.summary();
		this.content = replyUpdate.content();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof final Reply reply)) {
			return false;
		}
		return Objects.equals(id, reply.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

}
