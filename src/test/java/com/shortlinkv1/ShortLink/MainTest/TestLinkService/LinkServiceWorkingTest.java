package com.shortlinkv1.ShortLink.MainTest.TestLinkService;

import com.shortlinkv1.Backend.repository.validation.CreateLinkRequest;
import com.shortlinkv1.Backend.entity.linkEntity.ShortLink;
import com.shortlinkv1.Backend.entity.tagEntity.Tag;
import com.shortlinkv1.Backend.entity.userEntity.User;
import com.shortlinkv1.Backend.repository.ShortLink.LinkRepository;
import com.shortlinkv1.Backend.repository.Tag.TagRepository;
import com.shortlinkv1.Backend.service.link.LinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LinkServiceWorkingTest {

    @Mock
    private LinkRepository linkRepository;

    @Mock
    private TagRepository tagRepository;

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

        String originalUrl = "https://example.com  ";
        List<String> listOfTags = List.of("develop", "contribute");

        CreateLinkRequest request = new CreateLinkRequest(originalUrl, listOfTags, null);

        Tag existingTag = new Tag("develop");

        when(tagRepository.findByName("develop")).thenReturn(Optional.of(existingTag));
        when(tagRepository.findByName("contribute")).thenReturn(Optional.empty());

        when(tagRepository.save(any(Tag.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(linkRepository.findByShortCode(anyString()))
                .thenReturn(Optional.empty());

        when(linkRepository.save(any(ShortLink.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ShortLink result = linkService.createShortlink(request, testUser);

        assertNotNull(result);

        assertEquals("https://example.com", result.getOriginalUrl().trim());
        assertNotNull(result.getShortCode());
        assertEquals(8, result.getShortCode().length());
        assertEquals(testUser, result.getUser());

        Set<Tag> tags = result.getTags();

        assertEquals(2, tags.size());
        assertTrue(tags.stream().anyMatch(t -> "develop".equals(t.getName())));
        assertTrue(tags.stream().anyMatch(t -> "contribute".equals(t.getName())));

        verify(tagRepository).save(argThat(tag -> "contribute".equals(tag.getName())));
        verify(linkRepository).save(any(ShortLink.class));

    }
}