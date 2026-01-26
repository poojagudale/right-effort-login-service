package com.example.MyProject.service;

import com.example.MyProject.model.User;
import com.example.MyProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GoogleUserService {

    @Autowired
    private UserRepository userRepository;

    public User processGoogleUser(String email, String name, String googleId) {

        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            return existingUser.get();
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setProvider("GOOGLE");
        newUser.setProviderId(googleId);
        newUser.setPassword(null);
        newUser.setRole("ROLE_USER");

        return userRepository.save(newUser);
    }
}
