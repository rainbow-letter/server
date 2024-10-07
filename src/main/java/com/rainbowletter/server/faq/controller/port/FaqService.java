package com.rainbowletter.server.faq.controller.port;

import com.rainbowletter.server.faq.dto.FaqAdminResponses;
import com.rainbowletter.server.faq.dto.FaqCreate;
import com.rainbowletter.server.faq.dto.FaqSwitchSequence;
import com.rainbowletter.server.faq.dto.FaqUpdate;
import com.rainbowletter.server.faq.dto.FaqUserResponses;

public interface FaqService {

	FaqAdminResponses findAll();

	FaqUserResponses findAllByVisibility();

	Long create(FaqCreate request);

	void update(Long id, FaqUpdate request);

	void changeVisibility(Long id);

	void switchSequence(FaqSwitchSequence request);

	void delete(Long id);

}
