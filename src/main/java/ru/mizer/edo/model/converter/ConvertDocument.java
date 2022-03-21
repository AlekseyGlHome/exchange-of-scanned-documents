package ru.mizer.edo.model.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mizer.edo.model.dto.DocumentDto;
import ru.mizer.edo.model.entity.Document;

@Service
@RequiredArgsConstructor
public class ConvertDocument {

    private final ConvertUser convertUser;

    public DocumentDto DocumentToDto(Document doc) {
        return DocumentDto.builder()
                .id(doc.getId())
                .docHtml(doc.getDocHtml())
                .docPath(doc.getDocPath())
                .header(doc.getHeader())
                .isDone(doc.getIsDone())
                .autor(convertUser.userToDto(doc.getAutor()))
                .userLastChange(convertUser.userToDto(doc.getUserLastChange()))
                .build();
    }
}
