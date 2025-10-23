package com.shortlinkv1.ShortLink.service.link;

import com.shortlinkv1.ShortLink.entity.ShortLink;
import com.shortlinkv1.ShortLink.entity.userEntity.User;
import com.shortlinkv1.ShortLink.repository.ShortLink.LinkRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LinkService {

    private final LinkRepository linkRepository;
    private static final int CODE_LENGTH = 8;
    private static final String CHARACTERS = "0123456789ABCDEFGHIJ";
    private static final SecureRandom random = new SecureRandom();

    private final ConcurrentHashMap<String, Integer> linkLimitBank = new ConcurrentHashMap<>();


    private String getHourKey (String userId) {
        LocalDateTime now = LocalDateTime.now();

        return userId + "_" + now.getYear() + "-" +
                now.getMonthValue() + "-" +
                now.getDayOfMonth() + "T" +
                now.getHour();
    }


    private Boolean isAllowedToCreateLink(String userId){
        String hourKey = getHourKey(userId);

        return linkLimitBank.compute(hourKey, (key, count) -> {

            if (count == null) {
                return 1;
            }
            if (count >= 10) {
                return count;
            }

            return count + 1;
        }) <=10;
    }


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

        if (!isAllowedToCreateLink(user.getId().toString())) {
            throw new RuntimeException("Too many requests: maximum 10 links per hour");
        }

        String shortCode;
        do {
            shortCode = generateShortCode();
        } while (linkRepository.findByShortCode(shortCode).isPresent());

        ShortLink link = new ShortLink(originalUrl, shortCode, user);
        return linkRepository.save(link);
    }

    @Transactional
    public ShortLink update(ShortLink link) {
        return linkRepository.save(link);
    }

    public ShortLink findByShortCode(String shortCode) {
        return linkRepository.findByShortCode(shortCode).
                orElse(null);
    }

    public List<ShortLink> findByUser(User user) {
        return linkRepository.findByUser(user);
    }

}
