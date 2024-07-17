package com.rainbowletter.server.common.infrastructure;

import com.rainbowletter.server.common.application.port.RandomHolder;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class SystemRandomHolder implements RandomHolder {

	private final Random random;

	public SystemRandomHolder() {
		this.random = new Random();
	}

	@Override
	public int random(final int min, final int max) {
		return random.nextInt(max - min) + min;
	}

}
