package com.rainbowletter.server.notification.domain;

import static com.rainbowletter.server.notification.domain.AlimTalkButtonType.BK;
import static com.rainbowletter.server.notification.domain.AlimTalkButtonType.DS;
import static com.rainbowletter.server.notification.domain.AlimTalkButtonType.MD;
import static com.rainbowletter.server.notification.domain.AlimTalkButtonType.WL;

public record AlimTalkButton(
		String linkType,
		String linkTypeName,
		String name,
		String linkMo,
		String linkPc,
		String schemeIos,
		String schemeAndroid
) {

	public static AlimTalkButton createWebLink(final String name, final String link) {
		return new AlimTalkButton(WL.name(), WL.getButtonName(), name, link, link, null, null);
	}

	public static AlimTalkButton createBotKeyword(final String name) {
		return new AlimTalkButton(BK.name(), BK.getButtonName(), name, null, null, null, null);
	}

	public static AlimTalkButton createDelivery() {
		return new AlimTalkButton(DS.name(), DS.getButtonName(), DS.getButtonName(), null, null, null, null);
	}

	public static AlimTalkButton createMessageServe(final String name) {
		return new AlimTalkButton(MD.name(), MD.getButtonName(), name, null, null, null, null);
	}

}
