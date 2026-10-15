package com.shopsphere;

// PhysicalProduct inherits from Product (OOP Inheritance & Polymorphism)
public class PhysicalProduct extends Product {
    private double weightKg;

    public PhysicalProduct(int id, String title, String description, double price, int stock, 
                           String category, int sellerId, double weightKg) {
        super(id, title, description, price, stock, category, sellerId);
        this.weightKg = weightKg;
    }

    public PhysicalProduct(String title, String description, double price, int stock, 
                           String category, int sellerId, double weightKg) {
        this(0, title, description, price, stock, category, sellerId, weightKg);
    }

    public double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(double weightKg) {
        this.weightKg = weightKg;
    }

    @Override
    public String getProductType() {
        return "PHYSICAL";
    }

    // Shipping fee calculated based on weight (Polymorphism)
    @Override
    public double calculateShippingFee(int quantity) {
        double fee = weightKg * quantity * 40.0;
        if (fee < 50.0) {
            return 50.0; // Minimum shipping charge is Rs. 50
        }
        return fee;
    }

    @Override
    public String getDeliveryInfo() {
        return "Courier Delivery (" + weightKg + " kg) in 3-5 days";
    }
}
