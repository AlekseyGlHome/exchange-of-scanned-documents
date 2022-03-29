package ru.mizer.edo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mizer.edo.model.entity.FilesPath;
import ru.mizer.edo.repository.FileRepository;

@Service
@RequiredArgsConstructor
public class FilePathService {

    private final FileRepository fileRepository;

    public void addFile(FilesPath filesPath){
        fileRepository.save(filesPath);

    }
}
