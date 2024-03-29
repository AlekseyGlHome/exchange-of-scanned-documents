package ru.mizer.edo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.mizer.edo.api.response.DocResponse;
import ru.mizer.edo.model.dto.DocumentDto;
import ru.mizer.edo.model.dto.FilePathDto;
import ru.mizer.edo.model.dto.UserDto;
import ru.mizer.edo.service.DocumentService;
import ru.mizer.edo.service.FilePathService;
import ru.mizer.edo.service.UserService;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/document")
public class DocumentController {
    private final DocumentService docService;
    private final UserService userService;
    private final FilePathService filePathService;

    @GetMapping()
    @PreAuthorize("hasAuthority('user:moderate')||hasAuthority('user:write')")
    public String document(Principal principal, Model model,
                           @RequestParam(defaultValue = "1", value = "page") int page,
                           @RequestParam(defaultValue = "10", value = "limit") int limit,
                           @RequestParam(defaultValue = "new", value = "sorting") String sorting) {
        DocResponse docResponse = docService.findAll(page, limit, sorting, principal.getName());
        UserDto user = userService.getUserByName(principal.getName());
        model.addAttribute("docsResp", docResponse);
        model.addAttribute("user", user);
        int totalPages = docResponse.getTotalPage();
        if (totalPages > 0) {
            List<Integer> pageNumber = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumber", pageNumber);

        }
        return "index";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:moderate')||hasAuthority('user:write')")
    public String findById(Principal principal, @PathVariable int id, Model model) {
        UserDto user = userService.getUserByName(principal.getName());
        DocumentDto documentDto = docService.findById(id);
        Collection<FilePathDto> filesPathDto = filePathService.getFilesByDocumentId(documentDto.getId());
        model.addAttribute("doc", documentDto);
        model.addAttribute("files", filesPathDto);
        model.addAttribute("user", user);
        return "detailDocument";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('user:moderate')||hasAuthority('user:write')")
    public String newDoc(Principal principal, Model model) {
        UserDto user = userService.getUserByName(principal.getName());
        DocumentDto documentDto = DocumentDto.builder()
                .dateDoc(LocalDate.now())
                .isDone(false)
                .build();
        model.addAttribute("doc", documentDto);
        model.addAttribute("user", user);
        return "detailDocument";
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('user:moderate')||hasAuthority('user:write')")
    public String seveDoc(Principal principal, DocumentDto documentDto, @RequestParam("file") MultipartFile[] files) throws IOException {
        docService.saveDocument(documentDto, principal.getName(), files);
        return "redirect:/";
    }

    @GetMapping("/file/delete/{id}")
    @PreAuthorize("hasAuthority('user:moderate')||hasAuthority('user:write')")
    public String deleteFile(@PathVariable int id) {
        int doc_id = filePathService.delete(id);
        return "redirect:/document/" + doc_id;
    }

}
