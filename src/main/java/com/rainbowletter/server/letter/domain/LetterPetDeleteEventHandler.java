package com.rainbowletter.server.letter.domain;

import com.rainbowletter.server.letter.application.port.LetterRepository;
import com.rainbowletter.server.pet.domain.PetDeleteEvent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LetterPetDeleteEventHandler {

	private final LetterRepository letterRepository;

	@Async
	@EventListener
	@Transactional
	public void handle(final PetDeleteEvent event) {
		final List<Letter> letters = letterRepository.findAllByPetId(event.pet().getId());
		letters.forEach(Letter::delete);
		letterRepository.deleteAll(letters);
	}

}
