package com.example.MyProject.dto;

public class UserProfileDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String picture;
    private String provider;
    private String role;

    public UserProfileDTO() {
    }

    public UserProfileDTO(String email, String firstName, String lastName,
                          String mobileNo, String picture, String provider, String role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNo = mobileNo;
        this.picture = picture;
        this.provider = provider;
        this.role = role;
    }

    // Getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}