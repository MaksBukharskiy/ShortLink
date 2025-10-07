package com.shortlinkv1.ShortLink.service;

import com.shortlinkv1.ShortLink.entity.User;
import com.shortlinkv1.ShortLink.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Transactional
    public User register(String email, String password) {

        if(userRepository.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("User with email " + email + " already exists");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(email, encodedPassword);
        return userRepository.save(user);

    }

}
