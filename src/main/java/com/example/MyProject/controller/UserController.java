package com.example.MyProject.controller;

import com.example.MyProject.dto.BasicUserDto;
import com.example.MyProject.dto.ApiResponse;
import com.example.MyProject.dto.UserProfileDTO;
import com.example.MyProject.model.User;
import com.example.MyProject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping("/basic")
    public ResponseEntity<ApiResponse> addBasicUser(@Valid @RequestBody BasicUserDto dto) {
        User created = userService.createBasicUser(dto);
        return ResponseEntity.ok(new ApiResponse(true, "User created successfully", created));
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody BasicUserDto dto) {
        User updated = userService.updateUserDetails(dto);
        return ResponseEntity.ok(new ApiResponse(true, "User updated successfully", updated));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Not authenticated");
        }

        String email = null;
        if (authentication.getPrincipal() instanceof org.springframework.security.oauth2.core.user.OAuth2User oauthUser) {
            email = oauthUser.getAttribute("email");
        } else {
            email = authentication.getName();
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(404).body("No profile data found for " + email);
        }

        UserProfileDTO dto = new UserProfileDTO(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getMobileNo(),
                user.getPicture(),
                user.getProvider(),
                user.getRole()
        );

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok("Logged out successfully");
    }
}