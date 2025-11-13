package com.shortlinkv1.Backend.service.link;

import com.shortlinkv1.Backend.entity.linkEntity.ShortLink;
import com.shortlinkv1.Backend.entity.tagEntity.Tag;
import com.shortlinkv1.Backend.entity.userEntity.User;
import com.shortlinkv1.Backend.repository.ShortLink.LinkRepository;
import com.shortlinkv1.Backend.repository.validation.CreateLinkRequest;
import com.shortlinkv1.Backend.repository.Tag.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class LinkService {

    private final LinkRepository linkRepository;
    private final TagRepository tagRepository;

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
            if (count >= 5) {
                return count;
            }

            return count + 1;
        }) <=5;
    }


    @Autowired
    public LinkService(LinkRepository linkRepository, TagRepository tagRepository) {
        this.linkRepository = linkRepository;
        this.tagRepository = tagRepository;
    }


    public void assignTagsToLink(ShortLink link, List<String> tagNames) {

        Set<Tag> tags = new HashSet<>();

        if(tagNames != null) {
            for(String tagName : tagNames) {

                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagName);
                            return tagRepository.save(newTag);
                        });

                tags.add(tag);
            }
        }

        link.setTags(tags);

    }


    private String generateShortCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    @Transactional
    public ShortLink createShortlink(CreateLinkRequest request, User user) {

        if (!isAllowedToCreateLink(user.getId().toString())) {
            throw new RuntimeException("Too many requests: maximum 5 links per hour");
        }

        String shortCode;
        do {
            shortCode = generateShortCode();
        } while (linkRepository.findByShortCode(shortCode).isPresent());

        ShortLink link = new ShortLink(request.originalUrl(), shortCode, user);

        if(request.ttlDays() != null) {
            link.setExpiresAt(LocalDateTime.now().plusDays(request.ttlDays()));
        }

        assignTagsToLink(link, request.tags());

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
