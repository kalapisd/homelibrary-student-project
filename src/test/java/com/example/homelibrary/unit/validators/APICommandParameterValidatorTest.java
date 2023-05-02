package com.example.homelibrary.unit.validators;

import com.example.homelibrary.utils.validator.APICommandParameterValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class APICommandParameterValidatorTest {

    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    private final APICommandParameterValidator apiCommandParameterValidator = new APICommandParameterValidator();

    @Test
    void whenValidCommandAdded_ReturnsTrue(){
        String validCommand = "isbn";
        assertTrue(apiCommandParameterValidator.isValid(validCommand, constraintValidatorContext));
    }

    @Test
    void whenInvalidCommandAdded_ReturnsFalse(){
        String invalidCommand = "almakörte";
        assertFalse(apiCommandParameterValidator.isValid(invalidCommand, constraintValidatorContext));
    }
}

