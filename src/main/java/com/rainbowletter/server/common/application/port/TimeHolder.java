package com.rainbowletter.server.common.application.port;

import java.time.LocalDateTime;

public interface TimeHolder {

	LocalDateTime currentTime();

	long currentTimeMillis();

	long dayToMillis(int day);

	long minuteToMillis(int minute);

}
