package com.example.homelibrary.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PublishDateValidator implements ConstraintValidator<ValidPublishDate, String> {

    @Override
    public void initialize(ValidPublishDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        int publishYear = Integer.parseInt(value);
        return publishYear > 1800 && publishYear < 2024;
    }
}
