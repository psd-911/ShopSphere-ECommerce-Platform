package com.shopsphere;

// Custom Exception class for checking product stock (OOP Exception Handling)
public class OutOfStockException extends Exception {
    private int availableStock;

    public OutOfStockException(String message, int availableStock) {
        super(message);
        this.availableStock = availableStock;
    }

    public int getAvailableStock() {
        return availableStock;
    }
}
