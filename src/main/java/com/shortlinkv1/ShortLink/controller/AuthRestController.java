package com.shortlinkv1.ShortLink.controller;

import com.shortlinkv1.ShortLink.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final UserService userService;

    @Autowired
    public AuthRestController(UserService userService) {
        this.userService = userService;
    }

    public static class RegisterRequest {
        private String email;
        private String password;
    }


    @PostMapping("user/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest.email,  registerRequest.password);
    }

}

