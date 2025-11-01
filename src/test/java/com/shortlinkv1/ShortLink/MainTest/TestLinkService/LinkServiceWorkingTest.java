package com.shortlinkv1.ShortLink.MainTest.TestLinkService;

import com.shortlinkv1.Backend.entity.linkEntity.ShortLink;
import com.shortlinkv1.Backend.entity.userEntity.User;
import com.shortlinkv1.Backend.repository.ShortLink.LinkRepository;
import com.shortlinkv1.Backend.service.link.LinkService;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LinkServiceWorkingTest {

    @Mock
    private LinkRepository linkRepository;

    @InjectMocks
    private LinkService linkService;

    private User testUser;


    @BeforeEach
    void setUp() {
        testUser = new User("test@example.com", "encoded-pass");
        testUser.setId(1L);
    }

    @Test
    public void createShortLink_ShouldGenerateCodeAndSave() {

        String originalUrl = "https://example.com";
        String generatedCode = "ABC12345";

        when(linkRepository.findByShortCode(anyString())).thenReturn(Optional.empty());

        when(linkRepository.save(any(ShortLink.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ShortLink result = linkService.createShortlink(originalUrl, testUser);

        assertNotNull(result);

        assertEquals(originalUrl.trim(), result.getOriginalUrl().trim());

        assertNotNull(result.getShortCode());
        assertEquals(8, result.getShortCode().length());

        assertEquals(testUser, result.getUser());

        verify(linkRepository).save(any(ShortLink.class));

    }

}
