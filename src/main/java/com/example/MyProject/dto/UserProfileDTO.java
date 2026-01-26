package com.example.MyProject.dto;

public record UserProfileDTO(
        String email,
        String name,
        String picture,
        String provider,
        String role
) {}