package ru.mizer.edo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordChangeCheckConstraintValidator.class)
public @interface PasswordChangeCheck {
    String message() default "Минимум 8 знаков";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
