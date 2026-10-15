package com.shopsphere;

// Admin class inherits from User (OOP Inheritance)
public class Admin extends User {
    private String department;

    public Admin(int id, String username, String password, String fullName, String department) {
        super(id, username, password, fullName, "ADMIN");
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    // Overriding abstract method (Polymorphism)
    @Override
    public String getRoleDetails() {
        return "Admin - Department: " + department + ". Has full administrative control.";
    }
}
