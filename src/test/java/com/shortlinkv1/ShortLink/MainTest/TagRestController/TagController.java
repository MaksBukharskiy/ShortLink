package com.shortlinkv1.ShortLink.MainTest.TagRestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortlinkv1.Backend.ShortLinkApplication;
import com.shortlinkv1.Backend.entity.tagEntity.Tag;
import com.shortlinkv1.Backend.repository.Tag.TagRepository;
import com.shortlinkv1.Backend.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ShortLinkApplication.class)
@AutoConfigureMockMvc
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Authentication authentication;


    @BeforeEach
    void set_up(){
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
    }

    @Test
    void getAllTags_ShouldReturnAllTags() throws Exception {

        Tag tag1 = new Tag("Work");
        Tag tag2 = new Tag("Important");

        tagRepository.save(tag1);
        tagRepository.save(tag2);

        mockMvc.perform(get("/api/tags"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").value("Work"))
                .andExpect(jsonPath("$[1]").value("Important"))
                .andExpect(jsonPath("$.length()").value(2));

    }
}