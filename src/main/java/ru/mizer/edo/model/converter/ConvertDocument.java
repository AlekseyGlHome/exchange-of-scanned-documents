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
                .docHtml(doc.getDocHtml())
                .header(doc.getHeader())
                .isDone(doc.getIsDone())
                .autor(convertUser.userToDto(doc.getAutor()))
                .userLastChange(convertUser.userToDto(doc.getUserLastChange()))
                .dateLastEdit(doc.getDateLastEdit())
                .build();
    }
}
