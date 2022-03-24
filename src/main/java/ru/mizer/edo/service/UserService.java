package ru.mizer.edo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mizer.edo.exception.NotFoundException;
import ru.mizer.edo.model.converter.ConvertUser;
import ru.mizer.edo.model.dto.UserDto;
import ru.mizer.edo.model.entity.User;
import ru.mizer.edo.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ConvertUser convertUser;

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public UserDto getUserByName(String name) throws NotFoundException {
        return convertUser.userToDto(userRepository.findByName(name).orElseThrow(()-> new NotFoundException("User not found")));
    }
}
