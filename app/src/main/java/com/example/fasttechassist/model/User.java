package com.example.fasttechassist.model;

public class User {
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String role; // e.g., "Faculty", "Tech", "Admin"
    private String availability; // "Available" or "Not Available" for Tech members

    public User() { }

    public User(String userId, String name, String email, String phoneNumber, String role, String availability) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.availability = availability;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }
}
