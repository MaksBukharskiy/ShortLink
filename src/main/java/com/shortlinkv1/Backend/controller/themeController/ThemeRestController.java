package com.shortlinkv1.Backend.controller.themeController;

import com.shortlinkv1.Backend.entity.userEntity.User;
import com.shortlinkv1.Backend.models.dto.themeEnumModel.ThemeRequest;
import com.shortlinkv1.Backend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class ThemeRestController {

    @Autowired
    private UserService userService;

    @PatchMapping("/theme")
    public ResponseEntity<User> updateTheme(
            @RequestBody ThemeRequest themeRequest,
            @AuthenticationPrincipal User user) {

        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        try {
            User updatedUser = userService.updateTheme(user.getId(), themeRequest.getTheme());
            return ResponseEntity.ok(updatedUser);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        catch (Exception e) {
            System.out.println("\n Error 500");
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }
}