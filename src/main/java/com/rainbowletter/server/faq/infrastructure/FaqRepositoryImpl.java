package com.rainbowletter.server.faq.infrastructure;

import static com.rainbowletter.server.faq.domain.QFaq.faq;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rainbowletter.server.common.exception.RainbowLetterException;
import com.rainbowletter.server.faq.application.port.FaqRepository;
import com.rainbowletter.server.faq.domain.Faq;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FaqRepositoryImpl implements FaqRepository {

	private final JPAQueryFactory queryFactory;
	private final FaqJpaRepository faqJpaRepository;

	@Override
	public Faq save(final Faq faq) {
		return faqJpaRepository.save(faq);
	}

	@Override
	public void saveAll(final List<Faq> faqs) {
		faqJpaRepository.saveAll(faqs);
	}

	@Override
	public Faq findByIdOrElseThrow(final Long id) {
		return faqJpaRepository.findById(id)
				.orElseThrow(() -> new RainbowLetterException("FAQ 리소스를 찾을 수 없습니다.", id));
	}

	@Override
	public List<Faq> findAllInIds(final List<Long> ids) {
		return queryFactory.selectFrom(faq)
				.where(faq.id.in(ids))
				.fetch();
	}

	@Override
	public List<Faq> findAll() {
		return queryFactory.selectFrom(faq)
				.orderBy(faq.sequence.asc())
				.fetch();
	}

	@Override
	public List<Faq> findAllByVisibility() {
		return queryFactory.selectFrom(faq)
				.where(faq.visibility.isTrue())
				.orderBy(faq.sequence.asc())
				.fetch();
	}

	@Override
	public void delete(final Faq faq) {
		faqJpaRepository.delete(faq);
	}

}
