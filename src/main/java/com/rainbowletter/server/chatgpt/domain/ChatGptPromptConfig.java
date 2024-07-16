package com.rainbowletter.server.chatgpt.domain;

import static com.rainbowletter.server.chatgpt.domain.ChatGptPromptType.A;
import static com.rainbowletter.server.chatgpt.domain.ChatGptPromptType.B;

import com.rainbowletter.server.chatgpt.dto.ChatGptPromptRequest;
import com.rainbowletter.server.letter.domain.Letter;
import com.rainbowletter.server.pet.domain.Pet;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class ChatGptPromptConfig {

	private final ChatGptPrompt promptA = new ChatGptPrompt(A);
	private final ChatGptPrompt promptB = new ChatGptPrompt(B);

	public void updateAll(final Map<String, String> properties) {
		promptA.update(properties);
		promptB.update(properties);
	}

	public List<ChatGptPromptRequest> createMessages(
			final ChatGptPromptType promptType,
			final Pet pet,
			final Letter letter,
			final boolean isFirstLetter
	) {
		final ChatGptPrompt prompt = getPrompt(promptType);

		final String firstContentAdditionalPrompt =
				isFirstLetter ? prompt.getFirstContentAdditional().formatted(pet.getName(), pet.getName()) : "";
		final String contentPrompt = prompt.getContent()
				.formatted(
						pet.getOwner(),
						pet.getName(),
						letter.getContent(),
						pet.getOwner(),
						pet.getName(),
						pet.getSpecies(),
						pet.getOwner(),
						firstContentAdditionalPrompt
				);
		final String cautionPrompt = prompt.getCaution().formatted(pet.getName(), pet.getOwner());

		return List.of(
				new ChatGptPromptRequest("system", prompt.getSystem()),
				new ChatGptPromptRequest("user", contentPrompt + cautionPrompt)
		);
	}

	private ChatGptPrompt getPrompt(final ChatGptPromptType promptType) {
		return promptType == A ? promptA : promptB;
	}

}
