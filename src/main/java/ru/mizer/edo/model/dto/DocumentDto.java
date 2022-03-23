package ru.mizer.edo.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.mizer.edo.model.entity.User;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class DocumentDto {
    private Integer id;
    private LocalDateTime dateCreate;
    private LocalDateTime dateDoc;
    private Double sum;
    private String nomerDoc;
    private String supplier;
    private String docHtml;
    private String header;
    private Boolean isDone;
    private UserDto autor;
    private UserDto userLastChange;
    private LocalDateTime dateLastEdit;

}