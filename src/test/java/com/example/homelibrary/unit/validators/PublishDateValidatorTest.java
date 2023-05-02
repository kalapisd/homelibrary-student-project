package com.example.homelibrary.unit.validators;

import com.example.homelibrary.utils.validator.PublishDateValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PublishDateValidatorTest {

    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    private final PublishDateValidator publishDateValidator = new PublishDateValidator();

    @Test
    void whenValidPublishDateAdded_ReturnsTrue() {
        Integer validPublishDate = 2000;
        assertTrue(publishDateValidator.isValid(validPublishDate, constraintValidatorContext));
    }

    @Test
    void whenInvalidPublishDateAdded_ReturnsFalse() {
        Integer invalidPublishDate = 1000;
        assertFalse(publishDateValidator.isValid(invalidPublishDate, constraintValidatorContext));
    }
}

