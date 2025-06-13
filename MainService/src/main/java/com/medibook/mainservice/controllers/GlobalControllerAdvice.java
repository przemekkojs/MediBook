package com.medibook.mainservice.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("homePath")
    public String provideHomePath(HttpServletRequest request) {
        String uri = request.getRequestURI();

        if (uri.startsWith("/admin")) return "/admin";
        if (uri.startsWith("/doctor")) return "/doctor";
        if (uri.startsWith("/client")) return "/client";

        return "/";
    }
}
