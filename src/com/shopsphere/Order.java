package com.shopsphere;

// Simple Order Model Class
public class Order {
    private int id;
    private int customerId;
    private String orderDate;
    private String status;
    private double totalAmount;
    private String shippingAddress;

    public Order(int id, int customerId, String orderDate, String status, double totalAmount, String shippingAddress) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
    }

    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public String getOrderDate() { return orderDate; }
    public String getStatus() { return status; }
    public double getTotalAmount() { return totalAmount; }
    public String getShippingAddress() { return shippingAddress; }

    @Override
    public String toString() {
        return "Order #" + id + " - Total: Rs. " + totalAmount + " (" + status + ")";
    }
}
