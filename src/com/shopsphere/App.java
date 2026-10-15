package com.shopsphere;

// Main Entry Point for ShopSphere E-Commerce Project
public class App {

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("   Starting ShopSphere E-Commerce App    ");
        System.out.println("=========================================");

        // 1. Initialize SQLite Database tables & default sample records
        DBHelper.initDatabase();

        // 2. Open Login Window
        ShopApp.LoginDialog login = new ShopApp.LoginDialog();
        login.setVisible(true);
    }
}
