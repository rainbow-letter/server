package com.rainbowletter.server.data.domain;

import static lombok.AccessLevel.PROTECTED;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.domain.TimeEntity;
import com.rainbowletter.server.data.dto.DataCreate;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

@Getter
@Entity
@Table(name = "data")
@NoArgsConstructor(access = PROTECTED)
public class Data extends AbstractAggregateRoot<Data> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String event;

	private String device;

	private String deviceName;

	private String os;

	private String osName;

	private String osVersion;

	private String agent;

	private String agentName;

	private String agentVersion;

	@Embedded
	private TimeEntity timeEntity;

	public Data(final DataCreate dataCreate, final TimeHolder timeHolder) {
		this(null, dataCreate, timeHolder);
	}

	@Builder
	public Data(final Long id, final DataCreate dataCreate, final TimeHolder timeHolder) {
		this.id = id;
		this.event = dataCreate.event();
		this.device = dataCreate.device();
		this.deviceName = dataCreate.deviceName();
		this.os = dataCreate.os();
		this.osName = dataCreate.osName();
		this.osVersion = dataCreate.osVersion();
		this.agent = dataCreate.agent();
		this.agentName = dataCreate.agentName();
		this.agentVersion = dataCreate.agentVersion();
		this.timeEntity = new TimeEntity(timeHolder);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof final Data data)) {
			return false;
		}
		return Objects.equals(id, data.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

}
