package com.medibook.mainservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
public class PageClientController {

    @GetMapping
    public String clientIndex() {
        return "client/index";
    }

    @GetMapping("/details")
    public String clientDetails() {
        return "client/details";
    }

    @GetMapping("/myvisits")
    public String clientVisits() {
        return "client/myvisits";
    }

    @GetMapping("/signup")
    public String clientSignup() {
        return "client/signup";
    }

    @GetMapping("/account")
    public String clientAccount() {
        return "client/account";
    }

    @GetMapping("/redirect")
    public String clientRedirect() {
        return "client/redirect";
    }
}
