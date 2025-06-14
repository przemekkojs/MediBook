package com.medibook.mainservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class PageAdminController {

    @GetMapping
    public String adminIndex() {
        return "admin/index";
    }

    @GetMapping("/details")
    public String adminDetails() {
        return "admin/details";
    }

    @GetMapping("/specializations")
    public String adminSpecializations() {
        return "admin/specializations";
    }

    @GetMapping("/users")
    public String adminUsers() {
        return "admin/users";
    }

    @GetMapping("/account")
    public String adminAccount() {
        return "admin/account";
    }

    @GetMapping("/places")
    public String adminPlaces() {
        return "admin/places";
    }
}
