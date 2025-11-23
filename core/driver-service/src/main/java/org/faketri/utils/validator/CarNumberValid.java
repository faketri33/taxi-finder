package org.faketri.utils.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CarNumberValidator.class)
@Documented
public @interface CarNumberValid {
    String message() default "Invalid car number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
