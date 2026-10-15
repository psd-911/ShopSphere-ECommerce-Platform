package com.shopsphere;

// Abstract Base Class for all Users (OOP Abstraction & Inheritance)
public abstract class User {
    protected int id;
    protected String username;
    protected String password;
    protected String fullName;
    protected String role;

    // Constructor
    public User(int id, String username, String password, String fullName, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getRole() { return role; }

    // Abstract method to be implemented by child classes (Polymorphism)
    public abstract String getRoleDetails();

    @Override
    public String toString() {
        return fullName + " (" + role + ")";
    }
}
