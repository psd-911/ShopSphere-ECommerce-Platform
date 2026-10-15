package com.shopsphere;

// Seller class inherits from User (OOP Inheritance)
public class Seller extends User {
    private String storeName;

    public Seller(int id, String username, String password, String fullName, String storeName) {
        super(id, username, password, fullName, "SELLER");
        this.storeName = storeName;
    }

    public String getStoreName() {
        return storeName;
    }

    // Overriding abstract method (Polymorphism)
    @Override
    public String getRoleDetails() {
        return "Seller - Store: " + storeName + ". Can add products and manage inventory.";
    }
}
