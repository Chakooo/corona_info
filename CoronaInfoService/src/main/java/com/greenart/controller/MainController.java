package com.greenart.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class MainController {
    @GetMapping("/")
    public String getMain(){



        return "/index";
    }
}
