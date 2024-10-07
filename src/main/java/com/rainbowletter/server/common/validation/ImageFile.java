package com.rainbowletter.server.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageFileValidator.class)
public @interface ImageFile {

	String message() default "이미지가 첨부되지 않았거나, 유효하지 않은 이미지입니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
