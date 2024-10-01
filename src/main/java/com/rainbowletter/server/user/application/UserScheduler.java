package com.rainbowletter.server.user.application;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.domain.EventLog;
import com.rainbowletter.server.common.domain.EventLogger;
import com.rainbowletter.server.user.application.port.UserRepository;
import com.rainbowletter.server.user.domain.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserScheduler {

	private static final int USER_CLEAN_LEAVE_DAY = 7;

	private final TimeHolder timeHolder;
	private final UserRepository userRepository;
	private final EventLogger eventLogger;

	@Async
	@Scheduled(cron = "0 0 0 * * *")
	@Transactional
	public void cleanLeave() {
		final LocalDate currentDate = timeHolder.currentDate();
		final LocalDate beforeDate = currentDate.minusDays(USER_CLEAN_LEAVE_DAY + 1L);
		final LocalDateTime beforeDateTime = LocalDateTime.of(beforeDate, LocalTime.MAX);

		final List<EventLog> logs = new ArrayList<>();
		final List<User> users = userRepository.findAllLeaveByBeforeDate(beforeDateTime);
		for (final User user : users) {
			user.delete();
			final EventLog eventLog = createSuccessLog(user);
			logs.add(eventLog);
		}

		userRepository.deleteAll(users);
		eventLogger.log(logs);
	}

	private EventLog createSuccessLog(final User user) {
		final String message = """
				%s에 탈퇴 요청 후 %d일이 경과하여 사용자 [%s] 데이터를 삭제합니다.
				""".formatted(user.getTimeEntity().getUpdatedAt(), USER_CLEAN_LEAVE_DAY, user.getEmail());
		return EventLog.success(user.getId(), user.getId(), "USER", "DELETE", message, timeHolder);
	}

}
