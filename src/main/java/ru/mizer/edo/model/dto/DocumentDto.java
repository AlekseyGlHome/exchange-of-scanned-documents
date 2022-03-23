package ru.mizer.edo.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class DocumentDto {
    private Integer id;
    private LocalDateTime dateCreate;
    private LocalDate dateDoc;
    private Double sum;
    private String nomerDoc;
    private String supplier;
    private Boolean isDone;
    private String autor;
    private String userLastChange;
    private LocalDateTime dateLastEdit;

}