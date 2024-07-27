package com.rainbowletter.server.chatgpt.domain;

import com.rainbowletter.server.chatgpt.dto.ChatGptRequest;
import com.rainbowletter.server.common.application.port.RandomHolder;
import com.rainbowletter.server.common.domain.AbstractFileManager;
import com.rainbowletter.server.common.exception.RainbowLetterException;
import com.rainbowletter.server.letter.domain.Letter;
import com.rainbowletter.server.pet.domain.Pet;
import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ChatGptConfigurer extends AbstractFileManager {

	private static final String DEFAULT_CONFIG_DIR_NAME = "settings";
	private static final String CONFIG_PROPERTIES_FILE_NAME = "chatgpt.properties";
	private static final String CONFIG_PROPERTIES_FORMAT = """
			useABTest=%b
			selectPrompt=%s
			model=%s
			maxTokens=%d
			temperature=%.2f
			topP=%.2f
			frequencyPenalty=%.2f
			presencePenalty=%.2f
			stops=%s
			""";
	private static final String PROMPT_PROPERTIES_FILE_NAME = "chatgpt-prompt.txt";
	private static final String PROMPT_PROPERTIES_FORMAT = """
			prompt.config.a.system
			%s
			prompt.config.a.content
			%s
			prompt.config.a.first-content-additional
			%s
			prompt.config.a.caution
			%s
			prompt.config.b.system
			%s
			prompt.config.b.content
			%s
			prompt.config.b.first-content-additional
			%s
			prompt.config.b.caution
			%s
			""";

	private final ChatGptConfig config;
	private final ChatGptPromptConfig promptConfig;
	private final RandomHolder randomHolder;

	public ChatGptConfigurer(final RandomHolder randomHolder) {
		this.randomHolder = randomHolder;
		this.config = new ChatGptConfig();
		this.promptConfig = new ChatGptPromptConfig();
	}

	@PostConstruct
	public void initialize() {
		createBaseDir(DEFAULT_CONFIG_DIR_NAME);
		final File configFile = createAbsolutePath(DEFAULT_CONFIG_DIR_NAME, CONFIG_PROPERTIES_FILE_NAME).toFile();
		final File promptFile = createAbsolutePath(DEFAULT_CONFIG_DIR_NAME, PROMPT_PROPERTIES_FILE_NAME).toFile();

		if (!configFile.exists()) {
			config(config);
		}
		if (!promptFile.exists()) {
			config(promptConfig);
		}

		load();
	}

	public void config(final ChatGptConfig chatGptConfig) {
		final Path path = createAbsolutePath(DEFAULT_CONFIG_DIR_NAME, CONFIG_PROPERTIES_FILE_NAME);
		try (final BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			writer.write(CONFIG_PROPERTIES_FORMAT.formatted(
					chatGptConfig.isUseABTest(),
					chatGptConfig.getSelectPrompt(),
					chatGptConfig.getModel(),
					chatGptConfig.getMaxTokens(),
					chatGptConfig.getTemperature(),
					chatGptConfig.getTopP(),
					chatGptConfig.getFrequencyPenalty(),
					chatGptConfig.getPresencePenalty(),
					chatGptConfig.getStopsAsString()
			));
		} catch (final IOException exception) {
			throw new RainbowLetterException("ChatGpt 설정 저장에 실패하였습니다.", exception);
		}
	}

	public void config(final ChatGptPromptConfig chatGptPromptConfig) {
		final Path path = createAbsolutePath(DEFAULT_CONFIG_DIR_NAME, PROMPT_PROPERTIES_FILE_NAME);
		try (final BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			writer.write(PROMPT_PROPERTIES_FORMAT.formatted(
					chatGptPromptConfig.getPromptA().getSystem(),
					chatGptPromptConfig.getPromptA().getContent(),
					chatGptPromptConfig.getPromptA().getFirstContentAdditional(),
					chatGptPromptConfig.getPromptA().getCaution(),
					chatGptPromptConfig.getPromptB().getSystem(),
					chatGptPromptConfig.getPromptB().getContent(),
					chatGptPromptConfig.getPromptB().getFirstContentAdditional(),
					chatGptPromptConfig.getPromptB().getCaution()
			));
		} catch (final IOException exception) {
			throw new RainbowLetterException("ChatGpt 설정 저장에 실패하였습니다.", exception);
		}
	}

	public void load() {
		loadConfig();
		loadPrompt();
	}

	private void loadConfig() {
		final File file = createAbsolutePath(DEFAULT_CONFIG_DIR_NAME, CONFIG_PROPERTIES_FILE_NAME).toFile();
		final Properties properties = new Properties();
		try (final InputStream inputStream = new FileInputStream(file)) {
			properties.load(inputStream);
			config.update(properties);
		} catch (final IOException exception) {
			throw new RainbowLetterException("ChatGpt 설정을 불러오는데 실패하였습니다.", exception);
		}
	}

	private void loadPrompt() {
		final Path path = createAbsolutePath(DEFAULT_CONFIG_DIR_NAME, PROMPT_PROPERTIES_FILE_NAME);
		final Map<String, String> result = new HashMap<>();
		String resultKey = "";
		final StringBuilder value = new StringBuilder();

		try (final BufferedReader reader = Files.newBufferedReader(path)) {
			boolean isFirstLine = true;
			String line;
			while ((line = reader.readLine()) != null) {
				if (isFirstLine) {
					resultKey = line;
					isFirstLine = false;
					continue;
				}
				if (line.startsWith("prompt.config.")) {
					result.put(resultKey, value.toString());
					resultKey = line;
					value.setLength(0);
				} else {
					value.append(line).append("\n");
				}
			}
			result.put(resultKey, value.toString());
			promptConfig.updateAll(result);
		} catch (final IOException exception) {
			throw new RainbowLetterException("ChatGpt 프롬프트를 불러오는데 실패하였습니다.", exception);
		}
	}

	public ChatGptPromptType getPromptType() {
		if (config.isUseABTest()) {
			final int randomNumber = randomHolder.random(1, 10);
			return randomNumber < 5 ? ChatGptPromptType.A : ChatGptPromptType.B;
		} else {
			return config.getSelectPrompt();
		}
	}

	public ChatGptRequest createChatGptRequest(
			final ChatGptPromptType promptType,
			final Pet pet,
			final Letter letter,
			final boolean isFirstLetter
	) {
		return new ChatGptRequest(
				config.getModel(),
				promptConfig.createMessages(promptType, pet, letter, isFirstLetter),
				config.getMaxTokens(),
				config.getTemperature(),
				config.getTopP(),
				config.getFrequencyPenalty(),
				config.getPresencePenalty(),
				config.getStops()
		);
	}

}
