package ru.mizer.edo.model.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mizer.edo.model.dto.FilePathDto;
import ru.mizer.edo.model.entity.FilesPath;

@Component
@RequiredArgsConstructor
public class ConvertFilePath {
    
    private final ConvertDocument convertDoc;

    public FilePathDto filePathToDto(FilesPath filesPath){
        return FilePathDto.builder()
                .id(filesPath.getId())
                .doc(convertDoc.DocumentToDto(filesPath.getDoc()))
                .path(filesPath.getPath())
                .build();
    }
}
