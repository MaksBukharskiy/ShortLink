package com.shortlinkv1.ShortLink.controller;

import com.shortlinkv1.ShortLink.dto.LoginRequest;
import com.shortlinkv1.ShortLink.dto.RegisterRequest;
import com.shortlinkv1.ShortLink.repository.UserRepository;
import com.shortlinkv1.ShortLink.security.JwtTokenProvider;
import com.shortlinkv1.ShortLink.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final UserService userService;
    private final JwtTokenProvider jwtToken;

    @Autowired
    public AuthRestController(UserService userService,  JwtTokenProvider jwtToken,  UserRepository userRepository) {
        this.userService = userService;
        this.jwtToken = jwtToken;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest.email, registerRequest.password);
    }


    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest loginRequest) {

        var user = userService.findByEmail(loginRequest.email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Email"));


         if (!userService.checkPassword(loginRequest.password, user.getPasswordHash())){
             throw new IllegalArgumentException("Invalid Password");
         }

         return  jwtToken.generateToken(user.getEmail());

    }


}