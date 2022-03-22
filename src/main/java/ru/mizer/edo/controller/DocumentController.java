package ru.mizer.edo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mizer.edo.api.response.DocResponse;
import ru.mizer.edo.service.DocumentService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class DocumentController {
    private final DocumentService docService;

    @GetMapping
    public String index(Model model,
                        @RequestParam(defaultValue = "0", value = "offset") int offSet,
                        @RequestParam(defaultValue = "10", value = "limit") int limit){
        DocResponse docResponse = docService.findAll(offSet, limit);
        model.addAttribute("docsResp",docResponse);
        int totalPages = docResponse.getTotalPage();
        if (totalPages>0){
            List<Integer> pageNumber = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumber",pageNumber);
        }
        return "index";
    }
}
