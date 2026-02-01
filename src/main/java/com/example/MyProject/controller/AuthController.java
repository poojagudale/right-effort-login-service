package com.example.MyProject.controller;

import com.example.MyProject.dto.UserDto;
import com.example.MyProject.dto.UserDto;
import com.example.MyProject.dto.UserProfileDTO;
import com.example.MyProject.dto.ApiResponse;
import com.example.MyProject.model.User;
import com.example.MyProject.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // ✅ Manual login (email + password)
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        Optional<User> userOpt = authService.login(email, password);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Invalid credentials", null));
        }

        User user = userOpt.get();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserProfileDTO dto = new UserProfileDTO(
                user.getEmail(),
                user.getName(),
                user.getPicture(),
                user.getProvider(),
                user.getRole()
        );

        return ResponseEntity.ok(new ApiResponse(true, "Login successful", dto));
    }

    // ✅ Registration
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody UserDto dto) {
        User saved = authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "User registered successfully", saved));
    }
}