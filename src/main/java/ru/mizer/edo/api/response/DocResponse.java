package ru.mizer.edo.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mizer.edo.model.dto.DocumentDto;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocResponse {
    private int totalPage;
    private int number;
    private Collection<DocumentDto> documents;
    private boolean isNew;
}
