package com.rainbowletter.server.common.application.port;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface TimeHolder {

	LocalDateTime currentTime();

	LocalDate currentDate();

	long currentTimeMillis();

	long dayToMillis(int day);

	long minuteToMillis(int minute);

}
