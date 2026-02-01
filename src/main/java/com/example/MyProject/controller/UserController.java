package com.example.MyProject.controller;

import com.example.MyProject.dto.BasicUserDto;
import com.example.MyProject.dto.ApiResponse;
import com.example.MyProject.model.User;
import com.example.MyProject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ Endpoint for dashboard "Add New User"
    @PostMapping("/basic")
    public ResponseEntity<ApiResponse> addBasicUser(@Valid @RequestBody BasicUserDto dto) {
        User created = userService.createBasicUser(dto);
        return ResponseEntity.ok(new ApiResponse(true, "User created successfully", created));
    }

    // ✅ Endpoint for dashboard "Update User by Email"
    @PostMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody BasicUserDto dto) {
        User updated = userService.updateUserDetails(dto);
        return ResponseEntity.ok(new ApiResponse(true, "User updated successfully", updated));
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile() {
        User user = userService.findByEmail("poojagudale217@gmail.com");
        return ResponseEntity.ok(user);
    }
}