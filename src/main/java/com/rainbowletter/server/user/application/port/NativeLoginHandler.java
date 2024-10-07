package com.rainbowletter.server.user.application.port;

import com.rainbowletter.server.common.dto.TokenResponse;

public interface NativeLoginHandler {

	TokenResponse process(String email, String password);

}
