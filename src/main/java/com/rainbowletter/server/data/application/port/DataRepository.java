package com.rainbowletter.server.data.application.port;

import com.rainbowletter.server.data.domain.Data;

public interface DataRepository {

	Data save(Data data);

}
