package com.shopsphere;

// DigitalProduct inherits from Product (OOP Inheritance & Polymorphism)
public class DigitalProduct extends Product {
    private String downloadUrl;
    private double fileSizeMb;

    public DigitalProduct(int id, String title, String description, double price, int stock, 
                          String category, int sellerId, String downloadUrl, double fileSizeMb) {
        super(id, title, description, price, stock, category, sellerId);
        this.downloadUrl = downloadUrl;
        this.fileSizeMb = fileSizeMb;
    }

    public DigitalProduct(String title, String description, double price, int stock, 
                          String category, int sellerId, String downloadUrl, double fileSizeMb) {
        this(0, title, description, price, stock, category, sellerId, downloadUrl, fileSizeMb);
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public double getFileSizeMb() {
        return fileSizeMb;
    }

    @Override
    public String getProductType() {
        return "DIGITAL";
    }

    // Digital goods have Rs. 0 shipping fee (Polymorphism)
    @Override
    public double calculateShippingFee(int quantity) {
        return 0.0;
    }

    @Override
    public String getDeliveryInfo() {
        return "Instant Download (" + fileSizeMb + " MB)";
    }
}
