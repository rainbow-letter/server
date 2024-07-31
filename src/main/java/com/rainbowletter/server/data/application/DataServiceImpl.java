package com.rainbowletter.server.data.application;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.data.application.port.DataRepository;
import com.rainbowletter.server.data.controller.port.DataService;
import com.rainbowletter.server.data.domain.Data;
import com.rainbowletter.server.data.dto.DataCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataServiceImpl implements DataService {

	private final TimeHolder timeHolder;
	private final DataRepository dataRepository;

	@Override
	@Transactional
	public void create(final DataCreate dataCreate) {
		final Data data = new Data(dataCreate, timeHolder);
		dataRepository.save(data);
	}

}
