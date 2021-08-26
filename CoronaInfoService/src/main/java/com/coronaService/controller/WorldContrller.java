package com.coronaService.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WorldContrller {
    @GetMapping("/world")
    public String getWorldInfo(){



        return "/world/world";
    }
    
}
