package com.shopsphere;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// Generic Shopping Cart Class (Generics & Collections: List, Map, Set - 6 Marks)
// T can be any child class of Product (PhysicalProduct or DigitalProduct)
public class Cart<T extends Product> {

    // Map Collection: stores Product as key and quantity as value
    private HashMap<T, Integer> itemMap;

    public Cart() {
        itemMap = new HashMap<>();
    }

    // Add product to cart
    public void addItem(T product, int quantity) {
        if (product == null || quantity <= 0) return;
        if (itemMap.containsKey(product)) {
            int current = itemMap.get(product);
            itemMap.put(product, current + quantity);
        } else {
            itemMap.put(product, quantity);
        }
    }

    // Remove product from cart
    public void removeItem(T product) {
        itemMap.remove(product);
    }

    // Clear all items
    public void clear() {
        itemMap.clear();
    }

    // List Collection: Returns ArrayList of all products in cart
    public ArrayList<T> getItemList() {
        return new ArrayList<>(itemMap.keySet());
    }

    // Set Collection: Returns HashSet of unique categories (no duplicates)
    public Set<String> getCategories() {
        HashSet<String> categories = new HashSet<>();
        for (T p : itemMap.keySet()) {
            categories.add(p.getCategory());
        }
        return categories;
    }

    // Returns the map of items and quantities
    public HashMap<T, Integer> getItemMap() {
        return itemMap;
    }

    // Total quantity of items in cart
    public int getTotalItemCount() {
        int count = 0;
        for (int q : itemMap.values()) {
            count += q;
        }
        return count;
    }

    // Subtotal of products using standard for-each loop
    public double calculateSubtotal() {
        double sum = 0.0;
        for (Map.Entry<T, Integer> entry : itemMap.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            sum += p.getPrice() * qty;
        }
        return sum;
    }

    // Total shipping fee using Polymorphic method calculateShippingFee()
    public double calculateShippingTotal() {
        double totalShipping = 0.0;
        for (Map.Entry<T, Integer> entry : itemMap.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            totalShipping += p.calculateShippingFee(qty);
        }
        return totalShipping;
    }

    // Grand total
    public double calculateGrandTotal() {
        return calculateSubtotal() + calculateShippingTotal();
    }

    public boolean isEmpty() {
        return itemMap.isEmpty();
    }
}
