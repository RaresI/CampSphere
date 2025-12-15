package com.ecamp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/register")
    public String redirectToRegister() {
        return "redirect:/register.html";
    }

    @GetMapping("/login")
    public String redirectToLogin() {
        return "redirect:/login.html";
    }
}
