package com.rainbowletter.server.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.util.Objects;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

public class ImageFileValidator implements ConstraintValidator<ImageFile, MultipartFile> {

	@Override
	public boolean isValid(final MultipartFile file, final ConstraintValidatorContext constraintValidatorContext) {
		if (Objects.isNull(file) || file.isEmpty()) {
			return false;
		}

		try {
			final String mimeType = new Tika().detect(file.getInputStream());
			return mimeType.startsWith("image");
		} catch (final IOException exception) {
			return false;
		}
	}

}
