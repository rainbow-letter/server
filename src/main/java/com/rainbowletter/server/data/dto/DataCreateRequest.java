package com.rainbowletter.server.data.dto;

import jakarta.validation.constraints.NotBlank;
import nl.basjes.parse.useragent.UserAgent;

public record DataCreateRequest(
		@NotBlank
		String event
) {

	public DataCreate toDomainDto(final UserAgent userAgent) {
		return DataCreate.builder()
				.event(event)
				.device(userAgent.getValue("DeviceClass"))
				.deviceName(userAgent.getValue("DeviceName"))
				.os(userAgent.getValue("OperatingSystemClass"))
				.osName(userAgent.getValue("OperatingSystemName"))
				.osVersion(userAgent.getValue("OperatingSystemVersion"))
				.agent(userAgent.getValue("AgentClass"))
				.agentName(userAgent.getValue("AgentName"))
				.agentVersion(userAgent.getValue("AgentVersion"))
				.build();
	}

}
