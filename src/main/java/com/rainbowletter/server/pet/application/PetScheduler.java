package com.rainbowletter.server.pet.application;

import com.rainbowletter.server.pet.application.port.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PetScheduler {

	private final PetRepository petRepository;

	@Async
	@Transactional
	@Scheduled(cron = "0 0 0 * * *")
	public void resetFavorite() {
		petRepository.resetFavorite();
	}

}
