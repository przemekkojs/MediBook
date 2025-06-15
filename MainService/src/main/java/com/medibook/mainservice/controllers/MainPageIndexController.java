package com.medibook.mainservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageIndexController {
    @GetMapping("/login")
    public String mainIndex() {
        return "index";
    }

    @GetMapping("/contact")
    public String mainContact() {
        return "contact";
    }

    @GetMapping("/help")
    public String mainHelp() {
        return "help";
    }
}
