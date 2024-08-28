package com.rainbowletter.server.user.application;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.user.application.port.UserRepository;
import com.rainbowletter.server.user.domain.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

	@Async
	@Scheduled(cron = "0 0 0 * * *")
	@Transactional
	public void cleanLeave() {
		final LocalDate currentDate = timeHolder.currentDate();
		final LocalDate beforeDate = currentDate.minusDays(USER_CLEAN_LEAVE_DAY + 1L);
		final LocalDateTime beforeDateTime = LocalDateTime.of(beforeDate, LocalTime.MAX);

		final List<User> users = userRepository.findAllLeaveByBeforeDate(beforeDateTime);
		users.forEach(User::delete);
		userRepository.deleteAll(users);
	}

}
