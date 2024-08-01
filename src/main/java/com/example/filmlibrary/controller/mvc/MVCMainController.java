package com.example.filmlibrary.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MVCMainController {
    @GetMapping("/") //localhost:8080
    public String index() {
        return "index";
    }
}