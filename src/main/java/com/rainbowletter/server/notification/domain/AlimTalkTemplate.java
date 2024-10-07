package com.rainbowletter.server.notification.domain;

import java.util.List;

public interface AlimTalkTemplate {

	String subject(Object... args);

	String failSubject(Object... args);

	String content(Object... args);

	String failContent(Object... args);

	List<AlimTalkButton> buttons(Object... args);

}
