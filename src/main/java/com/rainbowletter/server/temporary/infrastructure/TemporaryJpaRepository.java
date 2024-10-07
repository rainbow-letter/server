package com.rainbowletter.server.temporary.infrastructure;

import com.rainbowletter.server.temporary.domain.Temporary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemporaryJpaRepository extends JpaRepository<Temporary, Long> {

}
