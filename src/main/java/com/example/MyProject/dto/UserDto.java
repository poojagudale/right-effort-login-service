package com.example.MyProject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDto {

    private String username;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String mobileNo;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    private String picture; // ✅ profile picture URL

    private String password; // optional for registration/login

    // ✅ Add back name for compatibility
    private String name;

    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // ✅ New getter/setter for name
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}