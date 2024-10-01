package com.rainbowletter.server.common.infrastructure;

import com.rainbowletter.server.common.domain.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventLogJpaRepository extends JpaRepository<EventLog, Long> {

}
