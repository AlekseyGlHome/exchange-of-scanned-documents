package ru.mizer.edo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.mizer.edo.exception.NotFoundException;
import ru.mizer.edo.model.converter.ConvertUser;
import ru.mizer.edo.model.dto.NewUserDto;
import ru.mizer.edo.model.dto.UserDto;
import ru.mizer.edo.model.entity.User;
import ru.mizer.edo.repository.DocumentRepository;
import ru.mizer.edo.repository.UserRepository;
import ru.mizer.edo.security.Util;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ConvertUser convertUser;
    private final Util util;
    private final DocumentRepository documentRepository;

    public long getCountByName(String name) {
        return userRepository.countByName(name);
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public UserDto getUserByName(String name) {
        return convertUser.userToDto(userRepository.findByName(name).orElseThrow(() -> new NotFoundException("User not found")));
    }

    public Collection<UserDto> getAll() {
        return userRepository.findAll().stream().map(convertUser::userToDto).collect(Collectors.toList());
    }

    public NewUserDto getById(int id) {
        return new NewUserDto(userRepository.getById(id));
    }

    public void deleteById(int id) {
        if (documentRepository.countByAutorOrUserLastChange(id) == 0) {
            userRepository.deleteById(id);
        }

    }

    public void add(NewUserDto newUserDto) {
        newUserDto.setPassword(util.passwordEncoder().encode(newUserDto.getPassword()));
        userRepository.save(new User(newUserDto));
    }

    public BindingResult userVerification(NewUserDto userDto, BindingResult result) {
        User user = userRepository.getById(userDto.getId());
        if (!user.getName().equals(userDto.getName())) {
            if (getCountByName(userDto.getName()) > 0) {
                result.addError(new FieldError("user", "name", "Имя не уникально"));
            }
        }
        if (!userDto.getNewPass().isBlank()) {
            if (userDto.getNewPass().length() <= 8) {
                result.addError(new FieldError("user", "password", "Минимум 8 знаков"));
            }
        }

        return result;
    }

    public void edit(NewUserDto newUserDto) {
        User user = userRepository.getById(newUserDto.getId());
        user.setName(newUserDto.getName());
        user.setIsActive(newUserDto.getIsActive());
        user.setIsModerator(newUserDto.getIsModerator());
        if (!newUserDto.getNewPass().isBlank()) {
            newUserDto.setPassword(util.passwordEncoder().encode(newUserDto.getNewPass()));
            user.setPassword(newUserDto.getPassword());
        }
        userRepository.save(user);
    }
}
