package com.example.MyProject.service;

import com.example.MyProject.dto.BasicUserDto;
import com.example.MyProject.model.User;
import com.example.MyProject.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ Create a new basic user (manual registration or dashboard add)
    public User createBasicUser(BasicUserDto dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setMobileNo(dto.getMobileNo());

        user.setRole("ROLE_USER");
        user.setProvider("LOCAL");

        // ✅ Encode password (fallback to default if not provided)
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        } else {
            user.setPassword(passwordEncoder.encode("default123"));
        }

        // ✅ Set profile picture (default if not provided)
        user.setPicture(dto.getPicture() != null ? dto.getPicture() : "/images/default-avatar.png");

        return userRepository.save(user);
    }

    // ✅ Update existing user by email
    public User updateUserDetails(BasicUserDto dto) {
        return userRepository.findByEmail(dto.getEmail())
                .map(user -> {
                    user.setFirstName(dto.getFirstName());
                    user.setLastName(dto.getLastName());
                    user.setMobileNo(dto.getMobileNo());
                    user.setPicture(dto.getPicture());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with email: " + dto.getEmail()));
    }

    // ✅ Find user by email safely
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}