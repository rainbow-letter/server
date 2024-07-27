package com.rainbowletter.server.image.domain;

import com.rainbowletter.server.pet.domain.Pet;
import com.rainbowletter.server.pet.domain.PetDeleteEvent;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImagePetDeleteEventHandler {

	private final ImageFileManager imageFileManager;

	@Async
	@EventListener
	public void handle(final PetDeleteEvent event) throws IOException {
		final Pet pet = event.pet();
		if (pet.hasImage()) {
			imageFileManager.delete(pet.getImage());
		}
	}

}
