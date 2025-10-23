package com.shortlinkv1.ShortLink.controller.linkController;

import com.shortlinkv1.ShortLink.entity.ShortLink;
import com.shortlinkv1.ShortLink.entity.userEntity.User;
import com.shortlinkv1.ShortLink.repository.ShortLink.validation.CreateLinkRequest;
import com.shortlinkv1.ShortLink.service.link.LinkService;
import com.shortlinkv1.ShortLink.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class ShortLinkRestController {

    private final LinkService linkService;
    private final UserService userService;

    @Autowired
    public ShortLinkRestController(LinkService linkService, UserService userService) {
        this.linkService = linkService;
        this.userService = userService;
    }

    @PostMapping("/api/links")
    @ResponseStatus(HttpStatus.CREATED)
    public ShortLink createLink(@Valid @RequestBody CreateLinkRequest request,
                                Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return linkService.createShortlink(request.originalUrl(), user);
    }

    @GetMapping("/{shortCode}")
    public void redirectToOriginal(@PathVariable String shortCode,
                                   HttpServletResponse response) throws IOException {
        ShortLink link = linkService.findByShortCode(shortCode);
        if (link != null) {
            link.setClickLinkCount(link.getClickLinkCount() + 1);
            linkService.update(link);
            log.info("Redirecting from /{} to {}", shortCode, link.getOriginalUrl());
            response.sendRedirect(link.getOriginalUrl());
        } else {
            log.warn("Short link not found: {}", shortCode);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Short link not found");
        }
    }

    @GetMapping("/api/links/statistics")
    public List<ShortLink> getUserLinks(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        log.info("Fetching links for user: {}", email);
        return linkService.findByUser(user);
    }
}