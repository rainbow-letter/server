package com.rainbowletter.server.data.infrastructure;

import com.rainbowletter.server.data.application.port.DataRepository;
import com.rainbowletter.server.data.domain.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DataRepositoryImpl implements DataRepository {

	private final DataJpaRepository dataJpaRepository;

	@Override
	public Data save(final Data data) {
		return dataJpaRepository.save(data);
	}

}
