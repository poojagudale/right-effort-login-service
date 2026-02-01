package com.example.MyProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true, unique = true)
    private String username;

    @Column(nullable = true)
    private String password;

    // ✅ Profile details
    @Column(nullable = true)
    private String firstName;

    @Column(nullable = true)
    private String lastName;

    @Column(nullable = true)
    private String mobileNo;

    // ✅ OAuth/general fields
    @Column(nullable = true)
    private String name; // full name (Google OAuth)

    @Column(nullable = true)
    private String provider; // e.g., GOOGLE or LOCAL

    @Column(nullable = true, unique = true)
    private String providerId; // Google "sub" or local user id

    @Column(nullable = false)
    private String role = "ROLE_USER"; // default role

    @Column(nullable = true)
    private String picture; // Google profile photo URL

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.provider = "LOCAL";
        this.role = "ROLE_USER";
    }

    // ✅ Getters & Setters
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() { return id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }

    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }
}