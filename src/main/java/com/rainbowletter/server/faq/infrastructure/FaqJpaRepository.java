package com.rainbowletter.server.faq.infrastructure;

import com.rainbowletter.server.faq.domain.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqJpaRepository extends JpaRepository<Faq, Long> {

}
