package com.rainbowletter.server.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class PetNameValidator implements ConstraintValidator<PetName, String> {

	private static final int MAX_PET_NAME_LENGTH = 20;

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext constraintValidatorContext) {
		if (!StringUtils.hasText(value)) {
			return false;
		}
		return value.length() <= MAX_PET_NAME_LENGTH;
	}

}
