package com.shortlinkv1.ShortLink.service.link;

import com.shortlinkv1.ShortLink.entity.ShortLink;
import com.shortlinkv1.ShortLink.entity.userEntity.User;
import com.shortlinkv1.ShortLink.repository.ShortLink.LinkRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class LinkService {

    private final LinkRepository linkRepository;
    private static final int CODE_LENGTH = 8;
    private static final String CHARACTERS = "0123456789ABCDEFGHIJ";
    private static final SecureRandom random = new SecureRandom();


    @Autowired
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    private String generateShortCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    @Transactional
    public ShortLink createShortlink(String originalUrl, User user) {
        String shortCode;
        do {
            shortCode = generateShortCode();
        } while (linkRepository.findByShortCode(shortCode).isPresent());

        ShortLink link = new ShortLink(originalUrl, shortCode, user);
        return linkRepository.save(link);
    }

    public ShortLink findByShortCode(String shortCode) {
        return linkRepository.findByShortCode(shortCode).
                orElse(null);
    }

}
