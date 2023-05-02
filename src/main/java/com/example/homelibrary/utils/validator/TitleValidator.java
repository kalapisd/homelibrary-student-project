package com.example.homelibrary.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TitleValidator implements ConstraintValidator<ValidTitle, String> {

    @Override
    public void initialize(ValidTitle constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.matches("^(?:[\\p{L}\\p{Mn}\\p{Pd}0-9;:!?.,()']+\\s?)+$");
    }
}
