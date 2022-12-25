package com.example.Catalog.RestControllers;

import com.example.Catalog.Models.User;
import com.example.Catalog.Repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "login")
    public ResponseEntity Login(HttpServletResponse response, String login, String password) {
        var user = userRepository.findByLogin(login);
        if (user.isEmpty() || !user.get().getPassword().equals(password))
            return ResponseEntity.notFound().build();
        var idCookie = new Cookie("id", user.get().getId().toString());
        var loginCookie = new Cookie("login", user.get().getLogin());
        var passwordCookie = new Cookie("password", user.get().getPassword());
        idCookie.setPath("/");
        loginCookie.setPath("/");
        passwordCookie.setPath("/");
        response.addCookie(idCookie);
        response.addCookie(loginCookie);
        response.addCookie(passwordCookie);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "reg")
    public ResponseEntity Registration(HttpServletResponse response, String login, String password, String email) {
        var user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        try {
            userRepository.save(user);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        var idCookie = new Cookie("id", user.getId().toString());
        var loginCookie = new Cookie("login", user.getLogin());
        var passwordCookie = new Cookie("password", user.getPassword());
        idCookie.setPath("/");
        loginCookie.setPath("/");
        passwordCookie.setPath("/");
        response.addCookie(idCookie);
        response.addCookie(loginCookie);
        response.addCookie(passwordCookie);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "logout")
    public void Logout(HttpServletResponse response) {
        var idCookie = new Cookie("id", null);
        var loginCookie = new Cookie("login", null);
        var passwordCookie = new Cookie("password", null);
        idCookie.setPath("/");
        loginCookie.setPath("/");
        passwordCookie.setPath("/");
        response.addCookie(idCookie);
        response.addCookie(loginCookie);
        response.addCookie(passwordCookie);
    }
}
