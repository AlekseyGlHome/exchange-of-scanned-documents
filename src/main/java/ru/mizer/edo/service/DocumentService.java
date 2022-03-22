package ru.mizer.edo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.mizer.edo.api.response.DocResponse;
import ru.mizer.edo.exception.NotFoundException;
import ru.mizer.edo.model.converter.ConvertDocument;
import ru.mizer.edo.model.dto.DocumentDto;
import ru.mizer.edo.model.entity.Document;
import ru.mizer.edo.repository.DocumentRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final ConvertDocument convertDocument;

    public DocResponse findAll(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page-1,limit);//getPageRequest(offset, limit);
        Page<Document> documents = documentRepository.findByIsDoneFalseOrderByDateCreate(pageRequest);
        Collection<DocumentDto> documentDtos = documents.stream().map(convertDocument::DocumentToDto).toList();

        return new DocResponse(documents.getTotalPages(), documents.getNumber(), documentDtos);
    }

    public DocumentDto findById(int id) throws NotFoundException {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Документ не найден"));
        return convertDocument.DocumentToDto(document);

    }

    private PageRequest getPageRequest(int offset, int limit) {
        int numberPage = offset / limit;
        return PageRequest.of(numberPage, limit);
    }
}
