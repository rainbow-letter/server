package com.rainbowletter.server.faq.application;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.faq.application.port.FaqRepository;
import com.rainbowletter.server.faq.controller.port.FaqService;
import com.rainbowletter.server.faq.domain.Faq;
import com.rainbowletter.server.faq.domain.FaqValidator;
import com.rainbowletter.server.faq.dto.FaqAdminResponses;
import com.rainbowletter.server.faq.dto.FaqCreate;
import com.rainbowletter.server.faq.dto.FaqSwitchSequence;
import com.rainbowletter.server.faq.dto.FaqUpdate;
import com.rainbowletter.server.faq.dto.FaqUserResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FaqServiceImpl implements FaqService {

	private final TimeHolder timeHolder;
	private final FaqValidator faqValidator;
	private final FaqRepository faqRepository;

	@Override
	public FaqAdminResponses findAll() {
		return FaqAdminResponses.from(faqRepository.findAll());
	}

	@Override
	public FaqUserResponses findAllByVisibility() {
		return FaqUserResponses.from(faqRepository.findAllByVisibility());
	}

	@Override
	@Transactional
	public Long create(final FaqCreate request) {
		final Faq faq = new Faq(request, timeHolder);
		faqRepository.save(faq);
		return faq.getId();
	}

	@Override
	@Transactional
	public void update(final Long id, final FaqUpdate request) {
		final Faq faq = faqRepository.findByIdOrElseThrow(id);
		faq.update(request);
		faqRepository.save(faq);
	}

	@Override
	@Transactional
	public void changeVisibility(final Long id) {
		final Faq faq = faqRepository.findByIdOrElseThrow(id);
		faq.changeVisibility();
		faqRepository.save(faq);
	}

	@Override
	@Transactional
	public void switchSequence(final FaqSwitchSequence request) {
		final List<Long> ids = List.of(request.id(), request.targetId());
		final List<Faq> faqs = faqRepository.findAllInIds(ids);
		faqValidator.validateFaqResources(faqs, ids);

		final Faq faq = faqs.get(0);
		final Faq targetFaq = faqs.get(1);
		faq.switchSequence(targetFaq);

		faqRepository.saveAll(List.of(faq, targetFaq));
	}

	@Override
	@Transactional
	public void delete(final Long id) {
		final Faq faq = faqRepository.findByIdOrElseThrow(id);
		faqRepository.delete(faq);
	}

}
