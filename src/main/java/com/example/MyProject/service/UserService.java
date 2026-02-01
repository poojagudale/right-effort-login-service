package com.example.MyProject.service;

import com.example.MyProject.dto.BasicUserDto;
import com.example.MyProject.model.User;
import com.example.MyProject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ Create a new basic user (dashboard add)
    public User createBasicUser(BasicUserDto dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setMobileNo(dto.getMobileNo());

        // default values for dashboard-created users
        user.setRole("ROLE_USER");
        user.setProvider("LOCAL");
        user.setPassword(null); // or generate a random password if needed
        user.setPicture(dto.getPicture()); // ✅ allow profile picture

        return userRepository.save(user);
    }

    // ✅ Update existing user by email
    public User updateUserDetails(BasicUserDto dto) {
        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found with email: " + dto.getEmail());
        }

        User user = optionalUser.get();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setMobileNo(dto.getMobileNo());
        user.setPicture(dto.getPicture()); // ✅ update profile picture too

        return userRepository.save(user);
    }

    // ✅ Find user by email (used in /profile endpoint)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}