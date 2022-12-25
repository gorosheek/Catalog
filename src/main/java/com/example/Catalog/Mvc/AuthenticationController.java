package com.example.Catalog.Mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value = "/auth")
public class AuthenticationController {
    @GetMapping(value = "login")
    public String Login() {
        return "login";
    }

    @GetMapping(value = "reg")
    public String Registration() {
        return "reg";
    }
}
