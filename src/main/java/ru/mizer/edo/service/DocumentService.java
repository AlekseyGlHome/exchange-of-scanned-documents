package ru.mizer.edo.service;

import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.hibernate.id.GUIDGenerator;
import org.hibernate.id.UUIDGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.mizer.edo.api.response.DocResponse;
import ru.mizer.edo.exception.NotFoundException;
import ru.mizer.edo.model.converter.ConvertDocument;
import ru.mizer.edo.model.dto.DocumentDto;
import ru.mizer.edo.model.entity.Document;
import ru.mizer.edo.model.entity.FilesPath;
import ru.mizer.edo.model.entity.User;
import ru.mizer.edo.repository.DocumentRepository;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final ConvertDocument convertDocument;
    private final UserService userService;
    private final FilePathService filePathService;

    public DocResponse findAll(int page, int limit, String sorting, String userName) {
        User user = userService.findByName(userName).orElseThrow(() -> new NotFoundException("User not found"));
        Page<Document> documents;
        PageRequest pageRequest = PageRequest.of(page - 1, limit);//getPageRequest(offset, limit);
        if (user.getIsModerator()) {
            documents = selectDocumentsAllUser(sorting, pageRequest);
        } else {
            documents = selectDocumentsByUser(sorting, user, pageRequest);
        }

        Collection<DocumentDto> documentDtos = documents.stream().map(convertDocument::DocumentToDto).toList();

        return new DocResponse(documents.getTotalPages(), documents.getNumber(), documentDtos, sorting.equals("new") == true ? true : false);
    }

    private Page<Document> selectDocumentsByUser(String sorting, User user, PageRequest pageRequest) {
        switch (sorting.toLowerCase()) {
            case "new":
                return documentRepository.findByIsDoneFalseAndAutorIdOrderByDateCreateDesc(user.getId(), pageRequest);
            default:
                return documentRepository.findByIsDoneTrueAndAutorIdOrderByDateLastEditDesc(user.getId(), pageRequest);
        }
    }

    private Page<Document> selectDocumentsAllUser(String sorting, PageRequest pageRequest) {
        switch (sorting.toLowerCase()) {
            case "new":
                return documentRepository.findByIsDoneFalseOrderByDateCreateDesc(pageRequest);
            default:
                return documentRepository.findByIsDoneTrueOrderByDateLastEditDesc(pageRequest);
        }
    }

    public DocumentDto findById(int id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Документ не найден"));
        return convertDocument.DocumentToDto(document);

    }

    public void saveDocument(DocumentDto documentDto, String userName, MultipartFile[] files) throws IOException {
        User user = userService.findByName(userName).orElseThrow(() -> new NotFoundException("User not found"));
        Document document;
        if (documentDto.getId() != null) {
            document = documentRepository.getById(documentDto.getId());
        } else {
            document = new Document();
            document.setAutor(user);
            document.setUserLastChange(user);
        }
        document.setDateDoc(documentDto.getDateDoc());
        document.setNomerDoc(documentDto.getNomerDoc());
        document.setSupplier(documentDto.getSupplier());
        document.setIsDone(documentDto.getIsDone());
        document.setSum(documentDto.getSum());
        document.setUserLastChange(user);
        documentRepository.save(document);
        filePathService.uploadFiles(document, files);
    }
}
