package ru.mizer.edo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordChangeCheckConstraintValidator implements ConstraintValidator<PasswordChangeCheck, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!value.isBlank()) {
            return value.length()>=8;
        }else {
            return true;
        }
    }
}
