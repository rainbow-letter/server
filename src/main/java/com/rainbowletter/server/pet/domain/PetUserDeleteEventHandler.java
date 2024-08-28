package com.rainbowletter.server.pet.domain;

import com.rainbowletter.server.pet.application.port.PetRepository;
import com.rainbowletter.server.user.domain.UserDeleteEvent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PetUserDeleteEventHandler {

	private final PetRepository petRepository;

	@Async
	@EventListener
	@Transactional
	public void handle(final UserDeleteEvent event) {
		final List<Pet> pets = petRepository.findAllByUserId(event.user().getId());
		pets.forEach(Pet::delete);
		petRepository.deleteAll(pets);
	}

}
