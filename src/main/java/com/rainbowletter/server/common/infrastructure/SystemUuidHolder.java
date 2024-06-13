package com.rainbowletter.server.common.infrastructure;

import com.rainbowletter.server.common.application.port.UuidHolder;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class SystemUuidHolder implements UuidHolder {

	@Override
	public UUID generate() {
		return UUID.randomUUID();
	}

}
