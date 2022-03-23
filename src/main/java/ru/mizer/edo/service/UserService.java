package ru.mizer.edo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mizer.edo.exception.NotFoundException;
import ru.mizer.edo.model.entity.User;
import ru.mizer.edo.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findByName(String name) throws NotFoundException {
        return userRepository.findByName(name).orElseThrow(()-> new NotFoundException("User not found"));
    }
}
