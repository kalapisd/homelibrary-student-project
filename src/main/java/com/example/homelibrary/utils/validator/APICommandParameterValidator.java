package com.example.homelibrary.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class APICommandParameterValidator implements ConstraintValidator<ValidAPICommandParameter, String> {

    @Override
    public void initialize(ValidAPICommandParameter constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.equals("isbn") || value.equals("intitle") ||
                value.equals("inauthor") || value.equals("intitle_inauthor");
    }
}
