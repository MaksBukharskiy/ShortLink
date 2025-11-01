package com.shortlinkv1.ShortLink.MainTest.TestMessage;

import com.shortlinkv1.Backend.ShortLinkApplication;
import com.shortlinkv1.Backend.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ShortLinkApplication.class)
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class TestOk {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPermitAll_ShouldReturn_OK() throws Exception{

        mockMvc.perform(get("/message/test/test-permit-all"))

                .andExpect(status().isOk())
                .andExpect(content().string("OK"));

    }

}
