package ru.mizer.edo.model.converter;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.mizer.edo.model.dto.UserDto;
import ru.mizer.edo.model.entity.User;

@Component
public class ConvertUser {
    public UserDto userToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .isModerator(user.getIsModerator())
                .build();
    }
}
