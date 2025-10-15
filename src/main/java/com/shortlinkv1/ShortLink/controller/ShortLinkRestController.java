package com.shortlinkv1.ShortLink.controller;

import com.shortlinkv1.ShortLink.entity.ShortLink;
import com.shortlinkv1.ShortLink.entity.UserEntity.User;
import com.shortlinkv1.ShortLink.service.LinkService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ShortLinkRestController {

    private final LinkService linkService;

    @Autowired
    public ShortLinkRestController(LinkService linkService) {
        this.linkService = linkService;
    }

    class CreateLinkRequest {
        public String originalUrl;
    }

    @PostMapping("/api/links")
    @ResponseStatus(HttpStatus.CREATED)
    public ShortLink createLink(@Valid @RequestBody CreateLinkRequest request,
                                Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return linkService.createShortlink(request.originalUrl, user);
    }

    @GetMapping("/{shortCode}")
    public void redirectToOriginal(@PathVariable String shortCode,
                                   HttpServletResponse response) throws IOException {
        ShortLink link = linkService.findByShortCode(shortCode);
        if (link != null) {
            response.sendRedirect(link.getOriginalUrl());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Short link not found");
        }
    }

}
