package ru.mizer.edo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mizer.edo.service.DocumentService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class DocumentController {
    private final DocumentService docService;

    @GetMapping
    public String index(Model model,
                        @RequestParam(defaultValue = "0", value = "offset") int offSet,
                        @RequestParam(defaultValue = "10", value = "limit") int limit){
        model.addAttribute("docs",docService.findAll(offSet, limit));
        return "index";
    }
}
