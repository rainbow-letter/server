package com.rainbowletter.server.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

public class UserEmailValidator implements ConstraintValidator<UserEmail, String> {

	private static final String EMAIL_REGEX = "^[\\w+-.*]+@[\\w-]+\\.[\\w-.]+$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext constraintValidatorContext) {
		if (!StringUtils.hasText(value)) {
			return false;
		}
		return EMAIL_PATTERN.matcher(value).matches();
	}

}
