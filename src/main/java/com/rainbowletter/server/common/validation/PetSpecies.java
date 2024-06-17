package com.rainbowletter.server.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PetSpeciesValidator.class)
public @interface PetSpecies {

	String message() default "반려 동물의 종류는 10자 이하로 입력해 주세요.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
