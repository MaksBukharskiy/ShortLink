package com.shortlinkv1.Backend.service.user;

import com.shortlinkv1.Backend.entity.userEntity.User;
import com.shortlinkv1.Backend.models.dto.themeEnumModel.Theme;
import com.shortlinkv1.Backend.repository.MyUser.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User updateTheme(Long id, String themeString){

        Theme theme = Theme.valueOf(themeString.toUpperCase());

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setUserTheme(theme);
        return userRepository.save(user);

    }
    
    @Transactional
    public User register(String email, String password) {

        if(userRepository.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("User with email " + email + " already exists");
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(email, encodedPassword);

        User savedUser = userRepository.save(user);
        return savedUser;

    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean checkPassword(String Password, String encodedPassword) {
        return passwordEncoder.matches(Password, encodedPassword);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Пользователь не найден: " + email));


        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPasswordHash())
                .authorities("ROLE_USER")
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
