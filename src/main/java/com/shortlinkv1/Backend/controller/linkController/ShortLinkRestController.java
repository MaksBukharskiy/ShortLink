package com.shortlinkv1.Backend.controller.linkController;

import com.shortlinkv1.Backend.entity.linkEntity.ShortLink;
import com.shortlinkv1.Backend.entity.userEntity.User;
import com.shortlinkv1.Backend.repository.ShortLink.validation.CreateLinkRequest;
import com.shortlinkv1.Backend.response.appResponse.LinkResponse;
import com.shortlinkv1.Backend.service.link.LinkService;
import com.shortlinkv1.Backend.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<LinkResponse> createLink(@Valid @RequestBody CreateLinkRequest request,
                                                   Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        MDC.put("userId", user.getId().toString());
        MDC.put("email", email);

        try {
            log.info("\nCreating short link for URL: {}\n", request.originalUrl());

            ShortLink link = linkService.createShortlink(request, user);

            log.info("\nShort link created: /{} -> {}, clickCount={}\n",
                    link.getShortCode(),
                    link.getOriginalUrl(),
                    link.getTags().stream().map(t -> t.getName()).toList());

            return ResponseEntity.status(HttpStatus.CREATED).body(LinkResponse.from(link));
        } finally {
            MDC.clear();
        }
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