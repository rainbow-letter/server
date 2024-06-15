package com.rainbowletter.server.notification.infrastructure;

import com.rainbowletter.server.notification.dto.AlimTalkSendResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface AlimTalkClient {

	@PostExchange("/akv10/alimtalk/send/")
	AlimTalkSendResponse sendAlimTalk(@RequestBody MultiValueMap<String, String> body);

}
