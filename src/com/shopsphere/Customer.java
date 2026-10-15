package com.shopsphere;

// Customer class inherits from User (OOP Inheritance)
public class Customer extends User {
    private String address;
    private double walletBalance;

    public Customer(int id, String username, String password, String fullName, String address, double walletBalance) {
        super(id, username, password, fullName, "CUSTOMER");
        this.address = address;
        this.walletBalance = walletBalance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    // Overriding abstract method (Polymorphism)
    @Override
    public String getRoleDetails() {
        return "Customer - Can browse items, manage cart, and place orders.";
    }
}
