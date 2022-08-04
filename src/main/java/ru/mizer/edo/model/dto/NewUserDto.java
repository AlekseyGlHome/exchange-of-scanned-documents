package ru.mizer.edo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mizer.edo.model.entity.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDto {

    private Integer id;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 5, max = 100,message = "Минимум 5 , максимум 100 знаков")
    private String name;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 8,message = "Минимум 8 знаков")
    private String password;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String newPass;
    private Boolean isActive;
    private Boolean isModerator;

    public NewUserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.isActive = user.getIsActive();
        this.isModerator = user.getIsModerator();
        this.newPass = user.getPassword();
    }
}