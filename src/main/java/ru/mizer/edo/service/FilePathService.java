package ru.mizer.edo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mizer.edo.model.converter.ConvertFilePath;
import ru.mizer.edo.model.dto.FilePathDto;
import ru.mizer.edo.model.entity.Document;
import ru.mizer.edo.model.entity.FilesPath;
import ru.mizer.edo.repository.FileRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FilePathService {

    @Value("${upload_file}")
    private String UPLOAD_DIR;

    private final FileRepository fileRepository;
    private final ConvertFilePath convertFilePath;

    public void addFile(FilesPath filesPath) {
        fileRepository.save(filesPath);
    }

    public Collection<FilePathDto> getFilesByDocumentId(int idDoc) {
        return fileRepository.findByDoc_Id(idDoc).stream().map(convertFilePath::filePathToDto).toList();
    }

    public int delete(int id) {
        //final String UPLOAD_DIR = "./uploads/";
        FilesPath filesPath = fileRepository.getById(id);
        fileRepository.delete(filesPath);

        File file = new File(UPLOAD_DIR + filesPath.getPath());
        file.delete();
        return filesPath.getDoc().getId();

    }


    public void uploadFiles(Document doc, MultipartFile[] files) throws IOException {


        if (files.length <= 0 || files[0].isEmpty()) {
            return;
        }
        String randomPath = randomPathGeneration(3);
        for (MultipartFile file : files) {
            File mkdir = new File(UPLOAD_DIR+randomPath);
            mkdir.mkdirs();
            String fileNewName = UUID.randomUUID().toString().replaceAll("-", "");
            String fileOrigName = file.getOriginalFilename();
            int index = fileOrigName.lastIndexOf('.');
            String ext = fileOrigName.substring(index + 1);
            Path path = Paths.get(UPLOAD_DIR+randomPath + fileNewName + "." + ext);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            FilesPath filesPath = new FilesPath();
            filesPath.setDoc(doc);
            filesPath.setPath(path.toString());
            addFile(filesPath);
        }
    }

    private String randomPathGeneration(int numberOfLetters) {
        StringBuilder path = new StringBuilder();
        int countPath = ((int)(Math.random()*3))+1;
        for (int i=0;i<countPath;i++){
            path.append(oneGeneratePath()).append("/");
        }
        return path.toString();
    }
    private String oneGeneratePath(){
        char[] alphabet = new char[]{'q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m'};
        StringBuilder pathTemp = new StringBuilder();
        for (int i=0;i<3;i++){
            int index = ((int)(Math.random()*26));
            pathTemp.append(alphabet[index]);
        }
        return pathTemp.toString();
    }
}
