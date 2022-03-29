package ru.mizer.edo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mizer.edo.model.converter.ConvertFilePath;
import ru.mizer.edo.model.dto.FilePathDto;
import ru.mizer.edo.model.entity.FilesPath;
import ru.mizer.edo.repository.FileRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FilePathService {

    private final FileRepository fileRepository;
    private final ConvertFilePath convertFilePath;

    public void addFile(FilesPath filesPath){
        fileRepository.save(filesPath);
    }

    public Collection<FilePathDto> getFilesByDocumentId(int idDoc){
        return fileRepository.findByDoc_Id(idDoc).stream().map(convertFilePath::filePathToDto).toList();
    }
}
