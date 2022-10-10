package ru.mizer.edo.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mizer.edo.model.dto.NewUserDto;
import ru.mizer.edo.model.entity.User;
import ru.mizer.edo.repository.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserRepository userRepository;


    @Override
    public boolean supports(Class<?> clazz) {
        return NewUserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NewUserDto userDto = (NewUserDto) target;
        Optional<User> findUser = userRepository.findByName(userDto.getName());
        if (findUser.isPresent() && userDto.getId() != findUser.get().getId()) {
            errors.rejectValue("name", "", "Имя не уникально");
        }


        if ((userDto.getId() == null) || (!userDto.getPassword().isBlank())) {
            if (userDto.getPassword().length() < 8) {
                errors.rejectValue("password", "", "Пароль должен содержать минимум 8 знаков");
            }
        }

    }
}
