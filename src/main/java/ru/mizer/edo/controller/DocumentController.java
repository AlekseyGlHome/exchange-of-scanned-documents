package ru.mizer.edo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mizer.edo.api.response.DocResponse;
import ru.mizer.edo.exception.NotFoundException;
import ru.mizer.edo.model.dto.DocumentDto;
import ru.mizer.edo.model.dto.UserDto;
import ru.mizer.edo.model.entity.User;
import ru.mizer.edo.service.DocumentService;
import ru.mizer.edo.service.UserService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("document")
public class DocumentController {
    private final DocumentService docService;
    private final UserService userService;

    @GetMapping()
    @PreAuthorize("hasAuthority('user:moderate')||hasAuthority('user:write')")
    public String document(Principal principal, Model model,
                           @RequestParam(defaultValue = "1", value = "page") int page,
                           @RequestParam(defaultValue = "10", value = "limit") int limit,
                           @RequestParam(defaultValue = "new", value = "sorting") String sorting) throws NotFoundException {
        DocResponse docResponse = docService.findAll(page, limit, sorting, principal.getName());
        model.addAttribute("docsResp",docResponse);
        int totalPages = docResponse.getTotalPage();
        if (totalPages>0){
            List<Integer> pageNumber = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumber",pageNumber);
            model.addAttribute("user",principal);
        }
        return "index";
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:moderate')||hasAuthority('user:write')")
    public String findById(Principal principal, @PathVariable int id, Model model) throws NotFoundException {
        UserDto user = userService.getUserByName(principal.getName());
        DocumentDto documentDto = docService.findById(id);
        model.addAttribute("doc",documentDto);
        model.addAttribute("user",user);
        return "detailDocument";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('user:moderate')||hasAuthority('user:write')")
    public String newDoc(Principal principal, Model model) throws NotFoundException {
        UserDto user = userService.getUserByName(principal.getName());
        DocumentDto documentDto = DocumentDto.builder()
                .dateDoc(LocalDate.now())
                .isDone(false)
                .build();
        model.addAttribute("doc",documentDto);
        model.addAttribute("user",user);
        return "detailDocument";
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('user:moderate')||hasAuthority('user:write')")
    public String seveDoc(Principal principal, DocumentDto documentDto) throws NotFoundException {
        docService.saveDocument(documentDto,principal.getName());
        return "redirect:/";
    }

}
