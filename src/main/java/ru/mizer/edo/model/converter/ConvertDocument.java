package ru.mizer.edo.model.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.mizer.edo.model.dto.DocumentDto;
import ru.mizer.edo.model.entity.Document;

@Component
@RequiredArgsConstructor
public class ConvertDocument {

    private final ConvertUser convertUser;

    public DocumentDto DocumentToDto(Document doc) {
        return DocumentDto.builder()
                .id(doc.getId())
                .dateCreate(doc.getDateCreate())
                .dateDoc(doc.getDateDoc())
                .sum(doc.getSum())
                .nomerDoc(doc.getNomerDoc())
                .supplier(doc.getSupplier())
                .isDone(doc.getIsDone())
                .autor(doc.getAutor().getName())
                .userLastChange(doc.getUserLastChange().getName())
                .dateLastEdit(doc.getDateLastEdit())
                .build();
    }
}
