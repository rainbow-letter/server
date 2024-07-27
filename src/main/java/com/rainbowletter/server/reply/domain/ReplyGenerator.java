package com.rainbowletter.server.reply.domain;

import com.rainbowletter.server.chatgpt.domain.ChatGptConfigurer;
import com.rainbowletter.server.chatgpt.domain.ChatGptPromptType;
import com.rainbowletter.server.chatgpt.dto.ChatGptRequest;
import com.rainbowletter.server.chatgpt.dto.ChatGptResponse;
import com.rainbowletter.server.chatgpt.infrastructure.ChatGptClient;
import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.letter.application.port.LetterRepository;
import com.rainbowletter.server.letter.domain.Letter;
import com.rainbowletter.server.pet.application.port.PetRepository;
import com.rainbowletter.server.pet.domain.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyGenerator {

	private final TimeHolder timeHolder;
	private final ChatGptClient chatGptClient;
	private final PetRepository petRepository;
	private final LetterRepository letterRepository;
	private final ChatGptConfigurer chatGptConfigurer;

	public Reply generate(final Letter letter) {
		final Pet pet = petRepository.findByIdAndUserIdOrElseThrow(letter.getPetId(), letter.getUserId());
		final Long letterCount = letterRepository.countByPetId(letter.getPetId());
		final boolean isFirstLetter = letterCount <= 1;
		final ChatGptPromptType promptType = chatGptConfigurer.getPromptType();

		final ChatGptRequest request = chatGptConfigurer.createChatGptRequest(promptType, pet, letter, isFirstLetter);
		final ChatGptResponse response = chatGptClient.execute(request);
		final String gptContent = response.choices().get(0).message().content();
		return new Reply(letter.getPetId(), letter.getId(), gptContent, promptType, timeHolder);
	}

	public Reply generate(final Long letterId) {
		final Letter letter = letterRepository.findByIdOrElseThrow(letterId);
		return generate(letter);
	}

}
