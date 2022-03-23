package ru.mizer.edo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mizer.edo.api.response.DocResponse;
import ru.mizer.edo.exception.NotFoundException;
import ru.mizer.edo.model.dto.DocumentDto;
import ru.mizer.edo.service.DocumentService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService docService;

    @GetMapping("/document")
    public String document(Model model,
                        @RequestParam(defaultValue = "1", value = "page") int page,
                        @RequestParam(defaultValue = "10", value = "limit") int limit,
                        @RequestParam(defaultValue = "new", value = "sorting") String sorting){
        DocResponse docResponse = docService.findAll(page, limit, sorting);
        model.addAttribute("docsResp",docResponse);
        int totalPages = docResponse.getTotalPage();
        if (totalPages>0){
            List<Integer> pageNumber = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumber",pageNumber);
        }
        return "index";
    }
    @GetMapping("/detail/{id}")
    public String findById(@PathVariable int id, Model model) throws NotFoundException {

        DocumentDto documentDto = docService.findById(id);
        model.addAttribute("doc",documentDto);
        return "detailDocument";
    }

}
