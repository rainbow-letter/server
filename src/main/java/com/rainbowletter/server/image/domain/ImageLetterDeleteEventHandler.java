package com.rainbowletter.server.image.domain;

import com.rainbowletter.server.letter.domain.Letter;
import com.rainbowletter.server.letter.domain.LetterDeleteEvent;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageLetterDeleteEventHandler {

	private final ImageFileManager imageFileManager;

	@Async
	@EventListener
	public void handle(final LetterDeleteEvent event) throws IOException {
		final Letter letter = event.letter();
		if (letter.hasImage()) {
			imageFileManager.delete(letter.getImage());
		}
	}

}
