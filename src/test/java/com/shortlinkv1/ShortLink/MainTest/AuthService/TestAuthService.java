package com.shortlinkv1.ShortLink.MainTest.AuthService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortlinkv1.Backend.ShortLinkApplication;
import com.shortlinkv1.Backend.models.dto.registerModel.RegisterRequest;
import com.shortlinkv1.Backend.repository.MyUser.UserRepository;
import com.shortlinkv1.Backend.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ShortLinkApplication.class)
@AutoConfigureMockMvc
public class TestAuthService {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
    }

    private String asJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }


    @Test
    @DisplayName("testing register of user, should return token and create user")
    public void register_ValidUser_ShouldReturnTokenAndCreateUser() throws Exception {
        RegisterRequest request = new RegisterRequest(
                "maksbuharskij16@gmail.com",
                "password123"
        );

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isNotEmpty());

    }

}

