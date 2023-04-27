package com.example.homelibrary.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PublishDateValidator implements ConstraintValidator<ValidPublishDate, Integer> {

    @Override
    public void initialize(ValidPublishDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return value > 1800 && value < 2024;
    }
}
