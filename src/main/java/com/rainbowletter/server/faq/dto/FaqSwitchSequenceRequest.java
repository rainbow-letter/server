package com.rainbowletter.server.faq.dto;

public record FaqSwitchSequenceRequest(Long id, Long targetId) {

	public FaqSwitchSequence toDomainDto() {
		return FaqSwitchSequence.of(id, targetId);
	}

}
