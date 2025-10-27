package com.shortlinkv1.Backend.controller.authController;

import com.shortlinkv1.Backend.entity.userEntity.User;
import com.shortlinkv1.Backend.models.dto.loginModel.LoginRequest;
import com.shortlinkv1.Backend.models.dto.registerModel.RegisterRequest;
import com.shortlinkv1.Backend.models.dto.tokenModel.JwtResponse;
import com.shortlinkv1.Backend.repository.MyUser.UserRepository;
import com.shortlinkv1.Backend.security.JwtTokenProvider;
import com.shortlinkv1.Backend.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<JwtResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = userService.register(registerRequest.getEmail(), registerRequest.getPassword());
        String token = jwtToken.generateToken(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new JwtResponse(token));
    }


    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest loginRequest) {

        var user = userService.findByEmail(loginRequest.email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Email"));


         if (!userService.checkPassword(loginRequest.password, user.getPasswordHash())){
             throw new IllegalArgumentException("Invalid Password");
         }

         return jwtToken.generateToken(user.getEmail());


    }


    @GetMapping("/test-permit-all")
    public String test() {
        return "OK";
    }


}