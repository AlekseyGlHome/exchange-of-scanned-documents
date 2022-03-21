package ru.mizer.edo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class defaultController {

    @GetMapping
    public String index(){
        return "index";
    }
}
