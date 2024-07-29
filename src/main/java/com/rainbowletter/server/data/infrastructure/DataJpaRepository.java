package com.rainbowletter.server.data.infrastructure;

import com.rainbowletter.server.data.domain.Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataJpaRepository extends JpaRepository<Data, Long> {

}
