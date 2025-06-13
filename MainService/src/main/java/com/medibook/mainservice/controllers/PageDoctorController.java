package com.medibook.mainservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor")
public class PageDoctorController {

    @GetMapping
    public String doctorIndex() {
        return "doctor/index";
    }

    @GetMapping("/details")
    public String doctorDetails() {
        return "doctor/details";
    }

    @GetMapping("/myvisits")
    public String doctorVisits() {
        return "doctor/myvisits";
    }

    @GetMapping("/workhours")
    public String doctorWorkhours() {
        return "doctor/workhours";
    }

    @GetMapping("/account")
    public String doctorAccount() {
        return "doctor/account";
    }
}
