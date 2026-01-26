package com.example.MyProject.controller;

import com.example.MyProject.dto.UserProfileDTO;
import com.example.MyProject.model.User;
import com.example.MyProject.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // ✅ Manual login (email + password) using JSON body
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        Optional<User> userOpt = authService.login(email, password);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        User user = userOpt.get();

        // ✅ Set authentication context so /api/user/profile works for LOCAL users
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // ✅ Return user details to frontend
        UserProfileDTO dto = new UserProfileDTO(
                user.getEmail(),
                user.getName(),
                user.getPicture(),
                user.getProvider(), // "LOCAL"
                user.getRole()
        );

        return ResponseEntity.ok(dto);
    }

    // ✅ Registration
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User saved = authService.register(user);
        return ResponseEntity.ok(saved);
    }
}