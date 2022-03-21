package ru.mizer.edo.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class DocumentDto {
    private Integer id;
    private String docHtml;
    private LocalDateTime dateCreate;
    private String docPath;
    private String header;
    private Boolean isDone;
    private UserDto autor;
    private UserDto userLastChange;

}