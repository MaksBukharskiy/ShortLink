package com.shortlinkv1.ShortLink.service;

import com.shortlinkv1.ShortLink.entity.User;
import com.shortlinkv1.ShortLink.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
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

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean checkPassword(String Password, String encodedPassword) {
        return passwordEncoder.matches(Password, encodedPassword);
    }

}
