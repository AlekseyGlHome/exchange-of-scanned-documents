package ru.mizer.edo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mizer.edo.model.converter.ConvertFilePath;
import ru.mizer.edo.model.dto.FilePathDto;
import ru.mizer.edo.model.entity.FilesPath;
import ru.mizer.edo.repository.FileRepository;

import java.io.File;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FilePathService {

    @Value("${upload_file}")
    private String UPLOAD_DIR;

    private final FileRepository fileRepository;
    private final ConvertFilePath convertFilePath;

    public void addFile(FilesPath filesPath){
        fileRepository.save(filesPath);
    }

    public Collection<FilePathDto> getFilesByDocumentId(int idDoc){
        return fileRepository.findByDoc_Id(idDoc).stream().map(convertFilePath::filePathToDto).toList();
    }

    public int delete(int id){
        //final String UPLOAD_DIR = "./uploads/";
        FilesPath filesPath = fileRepository.getById(id);
        fileRepository.delete(filesPath);

        File file = new File(UPLOAD_DIR+filesPath.getPath());
        file.delete();
        return filesPath.getDoc().getId();

    }
}
