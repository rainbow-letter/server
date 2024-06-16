package com.rainbowletter.server.faq.domain;

import com.rainbowletter.server.common.exception.RainbowLetterException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class FaqValidator {

	public void validateFaqResources(final List<Faq> faqs, final List<Long> ids) {
		if (faqs.size() != 2) {
			throw new RainbowLetterException("순서 변경에 필요한 FAQ 리소스를 찾지 못했습니다.",
					ids.stream()
							.map(String::valueOf)
							.collect(Collectors.joining(","))
			);
		}
	}

}
