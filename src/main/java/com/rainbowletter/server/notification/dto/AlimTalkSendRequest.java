package com.rainbowletter.server.notification.dto;

import com.rainbowletter.server.notification.domain.AlimTalkButton;
import java.util.List;
import lombok.Builder;

@Builder
public record AlimTalkSendRequest(
		String receiver,
		String templateCode,
		String title,
		String content,
		String failTitle,
		String failContent,
		AlimTalkSendButtonRequest buttons,
		boolean useEmTitle
) implements NotificationSendRequest {

	public record AlimTalkSendButtonRequest(List<AlimTalkButton> button) {

	}

}
