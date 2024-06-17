package com.rainbowletter.server.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class PetPersonalitiesValidator implements ConstraintValidator<PetPersonalities, Set<String>> {

	private static final int MAX_PERSONALITY_SIZE = 3;

	@Override
	public boolean isValid(final Set<String> strings, final ConstraintValidatorContext constraintValidatorContext) {
		return strings.size() <= MAX_PERSONALITY_SIZE;
	}

}
