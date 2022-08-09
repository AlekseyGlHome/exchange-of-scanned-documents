package ru.mizer.edo.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.mizer.edo.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DuplicateNameConstraintValidator implements ConstraintValidator<DuplicateName, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return userRepository.countByName(value) == 0;
    }
}
