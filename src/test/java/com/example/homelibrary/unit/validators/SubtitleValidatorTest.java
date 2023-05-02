package com.example.homelibrary.unit.validators;

import com.example.homelibrary.utils.validator.SubtitleValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubtitleValidatorTest {

    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    private final SubtitleValidator subTitleValidator = new SubtitleValidator();

    @Test
    void whenValidSubtitleAdded_ReturnsTrue() {
        String validSubtitle = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzŽžÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõöùúûüýþÿ0123456789;:!?.,()' ";
        assertTrue(subTitleValidator.isValid(validSubtitle, constraintValidatorContext));
    }

    @Test
    void whenInvalidTitleAdded_ReturnsFalse() {
        String inValidSubtitle = "*";
        assertFalse(subTitleValidator.isValid(inValidSubtitle, constraintValidatorContext));
    }
}