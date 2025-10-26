package com.shortlinkv1.ShortLink.MainTest.UserService;

import com.shortlinkv1.Backend.entity.userEntity.User;
import com.shortlinkv1.Backend.repository.MyUser.UserRepository;
import com.shortlinkv1.Backend.service.user.UserService;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestUserService {

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    UserRepository userRepository;

    @Test
    public void register_TestingPasswordEncoderAndUserSave() throws Exception {

        String email = "maksbuharskij16@gmail.com";
        String password = "12345678";
        String encodedPassword = "encoded-secret12345678";

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User result = userService.register(email, password);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals(encodedPassword, result.getPasswordHash());

    }


}
