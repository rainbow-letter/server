package com.rainbowletter.server.chatgpt.domain;

import static com.rainbowletter.server.chatgpt.domain.ChatGptPromptType.A;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import lombok.Getter;

@Getter
public class ChatGptConfig {

	private static final String STOPS_DELIMITER = ",";

	private boolean useABTest = false;
	private ChatGptPromptType selectPrompt = A;
	private String model = "gpt-4o";
	private long maxTokens = 1500L;
	private double temperature = 1.0;
	private double topP = 0.8;
	private double frequencyPenalty = 1.25;
	private double presencePenalty = 0;
	private List<String> stops = List.of("p.s");

	public void update(final Properties properties) {
		this.useABTest = Boolean.parseBoolean(properties.getProperty("useABTest"));
		this.selectPrompt = ChatGptPromptType.valueOf(properties.getProperty("selectPrompt"));
		this.model = properties.getProperty("model");
		this.maxTokens = Long.parseLong(properties.getProperty("maxTokens"));
		this.temperature = Double.parseDouble(properties.getProperty("temperature"));
		this.topP = Double.parseDouble(properties.getProperty("topP"));
		this.frequencyPenalty = Double.parseDouble(properties.getProperty("frequencyPenalty"));
		this.presencePenalty = Double.parseDouble(properties.getProperty("presencePenalty"));
		this.stops = Arrays.stream(properties.getProperty("stops").split(STOPS_DELIMITER)).toList();
	}

	public String getStopsAsString() {
		return String.join(STOPS_DELIMITER, stops);
	}

}
