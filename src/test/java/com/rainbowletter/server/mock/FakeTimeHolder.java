package com.rainbowletter.server.mock;

import com.rainbowletter.server.common.application.port.TimeHolder;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class FakeTimeHolder implements TimeHolder {

	private static final long DAY_TO_HOUR = 24L;
	private static final long HOUR_TO_MINUTE = 60L;
	private static final long MINUTE_TO_SECOND = 60L;
	private static final long SECOND_TO_MILLISECOND = 1000L;

	private final long timeMillis;

	public FakeTimeHolder(final long timeMillis) {
		this.timeMillis = timeMillis;
	}

	@Override
	public LocalDateTime currentTime() {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(timeMillis),
				TimeZone.getTimeZone("Asia/Seoul").toZoneId());
	}

	@Override
	public LocalDate currentDate() {
		return LocalDate.ofInstant(Instant.ofEpochSecond(timeMillis), TimeZone.getTimeZone("Asia/Seoul").toZoneId());
	}

	@Override
	public long currentTimeMillis() {
		return timeMillis;
	}

	@Override
	public long dayToMillis(final int day) {
		return day * DAY_TO_HOUR * HOUR_TO_MINUTE * MINUTE_TO_SECOND * SECOND_TO_MILLISECOND;
	}

	@Override
	public long minuteToMillis(final int minute) {
		return minute * MINUTE_TO_SECOND * SECOND_TO_MILLISECOND;
	}

}
