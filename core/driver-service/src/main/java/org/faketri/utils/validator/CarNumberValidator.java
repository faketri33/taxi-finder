package org.faketri.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Locale;

public class CarNumberValidator implements ConstraintValidator<CarNumberValid, String> {
    @Override
    public void initialize(CarNumberValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isBlank()) return false;
        return s.toUpperCase(Locale.ROOT)
                .matches("^[АВЕКМНОРСТУХ]{1}\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}$");
    }
}
