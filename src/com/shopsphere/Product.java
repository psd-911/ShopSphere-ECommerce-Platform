package com.shopsphere;

// Abstract Base Class for Products (OOP Abstraction & Interface)
public abstract class Product implements Searchable {
    protected int id;
    protected String title;
    protected String description;
    protected double price;
    protected int stock;
    protected String category;
    protected int sellerId;

    // Constructor with ID
    public Product(int id, String title, String description, double price, int stock, String category, int sellerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.sellerId = sellerId;
    }

    // Constructor for creating new product before database saves ID
    public Product(String title, String description, double price, int stock, String category, int sellerId) {
        this(0, title, description, price, stock, category, sellerId);
    }

    // Abstract methods implemented by child classes (Polymorphism)
    public abstract String getProductType();
    public abstract double calculateShippingFee(int quantity);
    public abstract String getDeliveryInfo();

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getSellerId() { return sellerId; }
    public void setSellerId(int sellerId) { this.sellerId = sellerId; }

    // Searchable interface implementation
    @Override
    public boolean matches(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return true;
        }
        String key = keyword.toLowerCase().trim();
        return title.toLowerCase().contains(key) || 
               description.toLowerCase().contains(key) || 
               category.toLowerCase().contains(key);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Product)) return false;
        Product p = (Product) obj;
        return this.id == p.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return title + " - Rs. " + price + " (Stock: " + stock + ")";
    }
}
