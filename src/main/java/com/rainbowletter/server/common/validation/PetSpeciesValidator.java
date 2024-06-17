package com.rainbowletter.server.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class PetSpeciesValidator implements ConstraintValidator<PetSpecies, String> {

	private static final int MAX_PET_SPECIES_LENGTH = 10;

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext constraintValidatorContext) {
		if (!StringUtils.hasText(value)) {
			return false;
		}
		return value.length() <= MAX_PET_SPECIES_LENGTH;
	}

}
