package com.example.homelibrary.utils.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = APICommandParameterValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAPICommandParameter {
    String message() default "API command parameter must be one of: isbn, intitle, inauthor, intitle_inauthor!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
