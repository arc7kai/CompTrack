package com.comptrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home") // Changed from "/" to "/home"
    public String home() {
        return "home"; // Returns view named "home"
    }
}