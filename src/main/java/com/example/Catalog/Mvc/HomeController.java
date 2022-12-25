package com.example.Catalog.Mvc;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class HomeController {
    @GetMapping
    public String Index(HttpServletResponse response, @CookieValue(value = "id", defaultValue = "") Integer id) {
        if (id == null) return "redirect:/login";
        return "technics";
    }

    @GetMapping(value = "addForm")
    public String AddForm(@CookieValue(value = "id", defaultValue = "") String id) {
        if (id.isEmpty()) return "redirect:/login";
        return "addForm";
    }

    @GetMapping(value = "updateForm/{cardId}")
    public String UpdateForm(@CookieValue(value = "id", defaultValue = "") String id, Model model, @PathVariable Integer cardId) {
        if (id.isEmpty()) return "redirect:/login";
        model.addAttribute("cardId", cardId);
        return "updateForm";
    }
}
