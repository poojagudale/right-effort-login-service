package com.example.MyProject.controller;

import com.example.MyProject.dto.UserProfileDTO;
import com.example.MyProject.model.User;
import com.example.MyProject.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {

    @Autowired
    private AuthService authService;

    @GetMapping("/profile")
    public ResponseEntity<?> profile(Authentication authentication,
                                     @AuthenticationPrincipal Object principal) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Not authenticated");
        }

        // Handle Google OAuth2 user
        if (principal instanceof DefaultOAuth2User oauthUser) {
            Map<String, Object> attrs = oauthUser.getAttributes();
            String email = (String) attrs.getOrDefault("email", "");
            String name = (String) attrs.getOrDefault("name", "");
            String picture = (String) attrs.getOrDefault("picture", "");

            UserProfileDTO dto = new UserProfileDTO(
                    email,
                    name,
                    picture,
                    "GOOGLE",
                    "USER"
            );
            return ResponseEntity.ok(dto);
        }

        // Handle LOCAL user via DB lookup
        String email = authentication.getName();
        User user = authService.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        UserProfileDTO dto = new UserProfileDTO(
                user.getEmail(),
                user.getName(),
                user.getPicture(),
                user.getProvider(), // "LOCAL"
                user.getRole()
        );
        return ResponseEntity.ok(dto);
    }
}