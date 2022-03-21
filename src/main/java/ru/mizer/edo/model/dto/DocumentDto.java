package ru.mizer.edo.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DocumentDto {
    private Integer id;
    private String docHtml;
    private String docPath;
    private String header;
    private Boolean isDone;
    private UserDto autor;
    private UserDto userLastChange;

}