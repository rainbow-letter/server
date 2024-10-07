package com.rainbowletter.server.pet.dto;

import java.util.List;

public record PetDashboardResponses(List<PetDashboardResponse> pets) {

	public static PetDashboardResponses from(final List<PetDashboardResponse> pets) {
		return new PetDashboardResponses(pets);
	}

}
