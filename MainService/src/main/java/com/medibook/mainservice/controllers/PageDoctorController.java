package com.medibook.mainservice.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/redirect")
    public String doctorRedirect() {
        return "doctor/redirect";
    }

    @GetMapping("/procedures")
    public String doctorProcedures() {
        return "doctor/procedures";
    }
}