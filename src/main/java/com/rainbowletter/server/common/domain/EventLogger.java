package com.rainbowletter.server.common.domain;

import com.rainbowletter.server.common.infrastructure.EventLogJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventLogger {

	private final EventLogJpaRepository eventLogJpaRepository;

	public void log(final EventLog eventLog) {
		eventLogJpaRepository.save(eventLog);
	}

	public void log(final List<EventLog> eventLogs) {
		eventLogJpaRepository.saveAll(eventLogs);
	}

}
