package com.shortlinkv1.Backend.response.appResponse;

import com.shortlinkv1.Backend.entity.linkEntity.ShortLink;
import com.shortlinkv1.Backend.entity.tagEntity.Tag;

import java.time.LocalDateTime;
import java.util.List;

public record LinkResponse (
       String shortCode,
       String originalUrl,
       LocalDateTime createdAt,
       List<String> tags
){
    public static LinkResponse from(ShortLink link){
        List<String> tagNames = link.getTags().stream()
                .map(Tag::getName)
                .toList();

        return new LinkResponse(
                link.getShortCode(),
                link.getOriginalUrl(),
                link.getCreatedAt(),
                tagNames
        );
    }

}
