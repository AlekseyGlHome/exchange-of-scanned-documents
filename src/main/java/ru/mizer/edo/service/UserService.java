package ru.mizer.edo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public Optional<User> findByNameAndActive(String name) {
        return userRepository.findByNameAndIsActiveTrue(name);
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


    public boolean edit(NewUserDto newUserDto, String activeUserName) {
        boolean isEditActiveUser = false;
        User user = userRepository.getById(newUserDto.getId());
        if (user.getName().equals(activeUserName)) {
            isEditActiveUser = true;
        }
        user.setName(newUserDto.getName());
        user.setIsActive(newUserDto.getIsActive());
        user.setIsModerator(newUserDto.getIsModerator());
        if (!newUserDto.getPassword().isBlank()) {
            newUserDto.setPassword(util.passwordEncoder().encode(newUserDto.getPassword()));
            user.setPassword(newUserDto.getPassword());
        }
        userRepository.save(user);
        return isEditActiveUser;
    }
}
