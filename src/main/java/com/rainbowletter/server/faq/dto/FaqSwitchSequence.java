package com.rainbowletter.server.faq.dto;

public record FaqSwitchSequence(Long id, Long targetId) {

	public static FaqSwitchSequence of(final Long id, final Long targetId) {
		return new FaqSwitchSequence(id, targetId);
	}

}
