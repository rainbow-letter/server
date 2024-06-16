package com.rainbowletter.server.faq.application.port;

import com.rainbowletter.server.faq.domain.Faq;
import java.util.List;

public interface FaqRepository {

	Faq save(Faq faq);

	void saveAll(List<Faq> faqs);

	Faq findByIdOrElseThrow(Long id);

	List<Faq> findAllInIds(List<Long> ids);

	List<Faq> findAll();

	List<Faq> findAllByVisibility();

	void delete(Faq faq);

}
