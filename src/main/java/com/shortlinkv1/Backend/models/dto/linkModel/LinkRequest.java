package com.shortlinkv1.Backend.models.dto.linkModel;

import java.util.List;

public record LinkRequest (
    String originalUrl,
    List<String> tags
){}
