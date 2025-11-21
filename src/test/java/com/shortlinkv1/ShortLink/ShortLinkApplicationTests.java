package com.shortlinkv1.ShortLink;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.shortlinkv1.Backend.ShortLinkApplication; // Импортируйте главный класс

@SpringBootTest(classes = ShortLinkApplication.class) // Явно укажите класс
class ShortLinkApplicationTests {

    @Test
    void contextLoads() {
    }
}