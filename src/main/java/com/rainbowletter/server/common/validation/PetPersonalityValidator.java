package com.rainbowletter.server.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class PetPersonalityValidator implements ConstraintValidator<PetPersonality, String> {

	private static final int MAX_PERSONALITY_LENGTH = 10;

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext constraintValidatorContext) {
		if (!StringUtils.hasText(value)) {
			return false;
		}
		return value.length() <= MAX_PERSONALITY_LENGTH;
	}

}
