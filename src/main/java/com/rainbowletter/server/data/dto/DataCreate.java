package com.rainbowletter.server.data.dto;

import lombok.Builder;

@Builder
public record DataCreate(
		String event,
		String device,
		String deviceName,
		String os,
		String osName,
		String osVersion,
		String agent,
		String agentName,
		String agentVersion
) {

}
