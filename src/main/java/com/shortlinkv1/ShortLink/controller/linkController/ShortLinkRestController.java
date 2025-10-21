package com.shortlinkv1.ShortLink.controller.linkController;

import com.shortlinkv1.ShortLink.entity.ShortLink;
import com.shortlinkv1.ShortLink.entity.userEntity.User;
import com.shortlinkv1.ShortLink.repository.ShortLink.validation.CreateLinkRequest;
import com.shortlinkv1.ShortLink.service.link.LinkService;
import com.shortlinkv1.ShortLink.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
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
            response.sendRedirect(link.getOriginalUrl());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Short link not found");
        }
    } 
}