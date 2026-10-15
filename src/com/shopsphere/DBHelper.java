package com.shopsphere;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

// Database Helper Class (JDBC Connectivity, CRUD & Transaction Management - 13 Marks Total)
public class DBHelper {

    private static Connection conn = null;

    // Singleton Database Connection Method
    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                // Load SQLite JDBC Driver
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:shopsphere.db");
            } catch (ClassNotFoundException e) {
                System.out.println("Driver Class not found: " + e.getMessage());
                throw new SQLException(e);
            }
        }
        return conn;
    }

    // Initialize Database Tables and Default Records
    public static void initDatabase() {
        try {
            Connection c = getConnection();
            Statement stmt = c.createStatement();

            // 1. Users Table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT UNIQUE, " +
                    "password TEXT, " +
                    "full_name TEXT, " +
                    "role TEXT, " +
                    "address TEXT, " +
                    "wallet_balance REAL DEFAULT 15000.0, " +
                    "store_name TEXT, " +
                    "department TEXT)");

            // 2. Products Table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS products (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title TEXT, " +
                    "description TEXT, " +
                    "price REAL, " +
                    "stock INTEGER, " +
                    "category TEXT, " +
                    "seller_id INTEGER, " +
                    "product_type TEXT, " +
                    "weight_kg REAL DEFAULT 1.0, " +
                    "download_url TEXT, " +
                    "file_size_mb REAL DEFAULT 0.0)");

            // 3. Orders Table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS orders (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "customer_id INTEGER, " +
                    "order_date TEXT, " +
                    "status TEXT, " +
                    "total_amount REAL, " +
                    "shipping_address TEXT)");

            // Insert initial seed data if table is empty
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
            if (rs.next() && rs.getInt(1) == 0) {
                // Seed Users
                stmt.executeUpdate("INSERT INTO users (username, password, full_name, role, department) " +
                        "VALUES ('admin', 'admin123', 'System Administrator', 'ADMIN', 'College Operations')");

                stmt.executeUpdate("INSERT INTO users (username, password, full_name, role, store_name) " +
                        "VALUES ('techstore', 'seller123', 'TechMart Electronics', 'SELLER', 'TechMart Gadgets')");

                stmt.executeUpdate("INSERT INTO users (username, password, full_name, role, address, wallet_balance) " +
                        "VALUES ('customer', 'buyer123', 'Demo Customer', 'CUSTOMER', 'Hostel Block-A, Greater Noida', 25000.0)");

                // Seed Products (Physical and Digital)
                stmt.executeUpdate("INSERT INTO products (title, description, price, stock, category, seller_id, product_type, weight_kg) " +
                        "VALUES ('Wireless Headphones', 'Over-ear bluetooth headset with mic', 3200.0, 10, 'Electronics', 2, 'PHYSICAL', 0.5)");

                stmt.executeUpdate("INSERT INTO products (title, description, price, stock, category, seller_id, product_type, weight_kg) " +
                        "VALUES ('Mechanical Gaming Keyboard', 'RGB blue switch mechanical keyboard', 2400.0, 15, 'Electronics', 2, 'PHYSICAL', 1.1)");

                stmt.executeUpdate("INSERT INTO products (title, description, price, stock, category, seller_id, product_type, weight_kg) " +
                        "VALUES ('Stainless Water Bottle 1L', 'Insulated vacuum flask', 650.0, 30, 'Lifestyle', 2, 'PHYSICAL', 0.4)");

                stmt.executeUpdate("INSERT INTO products (title, description, price, stock, category, seller_id, product_type, download_url, file_size_mb) " +
                        "VALUES ('Java Programming Guide PDF', 'Beginner to advanced Java notes eBook', 399.0, 100, 'Books', 2, 'DIGITAL', 'http://shopsphere.com/java.pdf', 15.0)");

                stmt.executeUpdate("INSERT INTO products (title, description, price, stock, category, seller_id, product_type, download_url, file_size_mb) " +
                        "VALUES ('Photo Filter Preset Pack', 'Collection of 40 Lightroom presets', 499.0, 200, 'Software', 2, 'DIGITAL', 'http://shopsphere.com/presets.zip', 50.0)");
            }
            rs.close();
            stmt.close();
            System.out.println("Database successfully initialized.");
        } catch (SQLException e) {
            System.out.println("Database initialization error: " + e.getMessage());
        }
    }

    // User Login using PreparedStatement
    public static User login(String username, String password) throws Exception {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();
        User user = null;
        if (rs.next()) {
            int id = rs.getInt("id");
            String u = rs.getString("username");
            String p = rs.getString("password");
            String name = rs.getString("full_name");
            String role = rs.getString("role");

            if ("ADMIN".equalsIgnoreCase(role)) {
                user = new Admin(id, u, p, name, rs.getString("department"));
            } else if ("SELLER".equalsIgnoreCase(role)) {
                user = new Seller(id, u, p, name, rs.getString("store_name"));
            } else {
                user = new Customer(id, u, p, name, rs.getString("address"), rs.getDouble("wallet_balance"));
            }
        }
        rs.close();
        ps.close();

        if (user == null) {
            throw new Exception("Invalid username or password!");
        }
        return user;
    }

    // Register a new customer
    public static boolean registerCustomer(String username, String password, String fullName, String address) throws Exception {
        String sql = "INSERT INTO users (username, password, full_name, role, address, wallet_balance) VALUES (?, ?, ?, 'CUSTOMER', ?, 10000.0)";
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ps.setString(3, fullName);
        ps.setString(4, address);

        int rows = ps.executeUpdate();
        ps.close();
        return rows > 0;
    }

    // Get all products (CRUD - Read)
    public static ArrayList<Product> getAllProducts() throws SQLException {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY id DESC";
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String desc = rs.getString("description");
            double price = rs.getDouble("price");
            int stock = rs.getInt("stock");
            String cat = rs.getString("category");
            int sellerId = rs.getInt("seller_id");
            String type = rs.getString("product_type");

            if ("DIGITAL".equalsIgnoreCase(type)) {
                list.add(new DigitalProduct(id, title, desc, price, stock, cat, sellerId, 
                        rs.getString("download_url"), rs.getDouble("file_size_mb")));
            } else {
                list.add(new PhysicalProduct(id, title, desc, price, stock, cat, sellerId, 
                        rs.getDouble("weight_kg")));
            }
        }
        rs.close();
        ps.close();
        return list;
    }

    // Get single product by ID
    public static Product getProductById(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        Product p = null;
        if (rs.next()) {
            String title = rs.getString("title");
            String desc = rs.getString("description");
            double price = rs.getDouble("price");
            int stock = rs.getInt("stock");
            String cat = rs.getString("category");
            int sellerId = rs.getInt("seller_id");
            String type = rs.getString("product_type");

            if ("DIGITAL".equalsIgnoreCase(type)) {
                p = new DigitalProduct(id, title, desc, price, stock, cat, sellerId, 
                        rs.getString("download_url"), rs.getDouble("file_size_mb"));
            } else {
                p = new PhysicalProduct(id, title, desc, price, stock, cat, sellerId, 
                        rs.getDouble("weight_kg"));
            }
        }
        rs.close();
        ps.close();
        return p;
    }

    // Add new product (CRUD - Create)
    public static boolean addProduct(Product p) throws SQLException {
        String sql = "INSERT INTO products (title, description, price, stock, category, seller_id, product_type, weight_kg, download_url, file_size_mb) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setString(1, p.getTitle());
        ps.setString(2, p.getDescription());
        ps.setDouble(3, p.getPrice());
        ps.setInt(4, p.getStock());
        ps.setString(5, p.getCategory());
        ps.setInt(6, p.getSellerId());
        ps.setString(7, p.getProductType());

        if (p instanceof PhysicalProduct) {
            PhysicalProduct phys = (PhysicalProduct) p;
            ps.setDouble(8, phys.getWeightKg());
            ps.setNull(9, java.sql.Types.VARCHAR);
            ps.setNull(10, java.sql.Types.DOUBLE);
        } else if (p instanceof DigitalProduct) {
            DigitalProduct dig = (DigitalProduct) p;
            ps.setNull(8, java.sql.Types.DOUBLE);
            ps.setString(9, dig.getDownloadUrl());
            ps.setDouble(10, dig.getFileSizeMb());
        }

        int rows = ps.executeUpdate();
        ps.close();
        return rows > 0;
    }

    // Update product stock (CRUD - Update)
    public static boolean updateStock(int productId, int newStock) throws SQLException {
        String sql = "UPDATE products SET stock = ? WHERE id = ?";
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, newStock);
        ps.setInt(2, productId);
        int rows = ps.executeUpdate();
        ps.close();
        return rows > 0;
    }

    // Delete product (CRUD - Delete)
    public static boolean deleteProduct(int productId) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, productId);
        int rows = ps.executeUpdate();
        ps.close();
        return rows > 0;
    }

    // Get all users (for Admin tab)
    public static ArrayList<User> getAllUsers() throws SQLException {
        ArrayList<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id ASC";
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String u = rs.getString("username");
            String p = rs.getString("password");
            String name = rs.getString("full_name");
            String role = rs.getString("role");

            if ("ADMIN".equalsIgnoreCase(role)) {
                list.add(new Admin(id, u, p, name, rs.getString("department")));
            } else if ("SELLER".equalsIgnoreCase(role)) {
                list.add(new Seller(id, u, p, name, rs.getString("store_name")));
            } else {
                list.add(new Customer(id, u, p, name, rs.getString("address"), rs.getDouble("wallet_balance")));
            }
        }
        rs.close();
        ps.close();
        return list;
    }

    // Update Customer wallet balance
    public static boolean updateWalletBalance(int customerId, double newBalance) throws SQLException {
        String sql = "UPDATE users SET wallet_balance = ? WHERE id = ?";
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setDouble(1, newBalance);
        ps.setInt(2, customerId);
        int rows = ps.executeUpdate();
        ps.close();
        return rows > 0;
    }

    // Fetch user by ID
    public static User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        User user = null;
        if (rs.next()) {
            int id = rs.getInt("id");
            String u = rs.getString("username");
            String p = rs.getString("password");
            String name = rs.getString("full_name");
            String role = rs.getString("role");

            if ("ADMIN".equalsIgnoreCase(role)) {
                user = new Admin(id, u, p, name, rs.getString("department"));
            } else if ("SELLER".equalsIgnoreCase(role)) {
                user = new Seller(id, u, p, name, rs.getString("store_name"));
            } else {
                user = new Customer(id, u, p, name, rs.getString("address"), rs.getDouble("wallet_balance"));
            }
        }
        rs.close();
        ps.close();
        return user;
    }

    // Get orders placed by customer
    public static ArrayList<Order> getOrdersByCustomer(int customerId) throws SQLException {
        ArrayList<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE customer_id = ? ORDER BY id DESC";
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new Order(
                    rs.getInt("id"),
                    rs.getInt("customer_id"),
                    rs.getString("order_date"),
                    rs.getString("status"),
                    rs.getDouble("total_amount"),
                    rs.getString("shipping_address")
            ));
        }
        rs.close();
        ps.close();
        return list;
    }

    // Get all orders across the system
    public static ArrayList<Order> getAllOrders() throws SQLException {
        ArrayList<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY id DESC";
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new Order(
                    rs.getInt("id"),
                    rs.getInt("customer_id"),
                    rs.getString("order_date"),
                    rs.getString("status"),
                    rs.getDouble("total_amount"),
                    rs.getString("shipping_address")
            ));
        }
        rs.close();
        ps.close();
        return list;
    }

    // =========================================================================
    // JDBC TRANSACTION MANAGEMENT (ACID - Commit & Rollback - 3 Marks Rubric)
    // =========================================================================
    public static int placeOrderTransaction(int customerId, String shippingAddress, Cart<Product> cart) 
            throws OutOfStockException, Exception {

        if (cart == null || cart.isEmpty()) {
            throw new Exception("Cart is empty!");
        }

        Connection conn = null;
        int generatedOrderId = 0;

        try {
            conn = getConnection();

            // STEP 1: Disable Auto-Commit to begin transaction
            conn.setAutoCommit(false);

            // STEP 2: Verify stock for all items
            String checkStockSql = "SELECT stock, title FROM products WHERE id = ?";
            PreparedStatement psCheckStock = conn.prepareStatement(checkStockSql);

            for (Map.Entry<Product, Integer> entry : cart.getItemMap().entrySet()) {
                Product p = entry.getKey();
                int requestedQty = entry.getValue();

                psCheckStock.setInt(1, p.getId());
                ResultSet rsStock = psCheckStock.executeQuery();

                if (rsStock.next()) {
                    int availableStock = rsStock.getInt("stock");
                    if (availableStock < requestedQty) {
                        rsStock.close();
                        psCheckStock.close();
                        // Rollback transaction and throw custom exception
                        throw new OutOfStockException("Not enough stock for " + p.getTitle() + 
                                " (Available: " + availableStock + ", Requested: " + requestedQty + ")", availableStock);
                    }
                }
                rsStock.close();
            }
            psCheckStock.close();

            // STEP 3: Verify customer wallet balance
            double grandTotal = cart.calculateGrandTotal();
            String checkBalSql = "SELECT wallet_balance FROM users WHERE id = ?";
            PreparedStatement psCheckBal = conn.prepareStatement(checkBalSql);
            psCheckBal.setInt(1, customerId);
            ResultSet rsBal = psCheckBal.executeQuery();

            if (rsBal.next()) {
                double currentBal = rsBal.getDouble("wallet_balance");
                if (currentBal < grandTotal) {
                    rsBal.close();
                    psCheckBal.close();
                    throw new Exception("Insufficient wallet balance! Available: Rs. " + currentBal + ", Required: Rs. " + grandTotal);
                }
            } else {
                rsBal.close();
                psCheckBal.close();
                throw new Exception("Customer account not found!");
            }
            rsBal.close();
            psCheckBal.close();

            // STEP 4: Insert record into orders table
            String orderSql = "INSERT INTO orders (customer_id, order_date, status, total_amount, shipping_address) VALUES (?, ?, 'CONFIRMED', ?, ?)";
            PreparedStatement psOrder = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            psOrder.setInt(1, customerId);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            psOrder.setString(2, sdf.format(new Date()));
            psOrder.setDouble(3, grandTotal);
            psOrder.setString(4, shippingAddress);
            psOrder.executeUpdate();

            ResultSet rsKeys = psOrder.getGeneratedKeys();
            if (rsKeys.next()) {
                generatedOrderId = rsKeys.getInt(1);
            }
            rsKeys.close();
            psOrder.close();

            // STEP 5: Decrement product stock in database
            String updateStockSql = "UPDATE products SET stock = stock - ? WHERE id = ?";
            PreparedStatement psStockUpdate = conn.prepareStatement(updateStockSql);

            for (Map.Entry<Product, Integer> entry : cart.getItemMap().entrySet()) {
                Product p = entry.getKey();
                int qty = entry.getValue();

                psStockUpdate.setInt(1, qty);
                psStockUpdate.setInt(2, p.getId());
                psStockUpdate.executeUpdate();
            }
            psStockUpdate.close();

            // STEP 6: Deduct customer wallet balance
            String deductBalSql = "UPDATE users SET wallet_balance = wallet_balance - ? WHERE id = ?";
            PreparedStatement psDeductBal = conn.prepareStatement(deductBalSql);
            psDeductBal.setDouble(1, grandTotal);
            psDeductBal.setInt(2, customerId);
            psDeductBal.executeUpdate();
            psDeductBal.close();

            // STEP 7: All steps succeeded -> COMMIT TRANSACTION
            conn.commit();
            System.out.println("Transaction Committed Successfully: Order #" + generatedOrderId + " placed! Total: Rs. " + grandTotal);
            return generatedOrderId;

        } catch (SQLException e) {
            // STEP 8: If any SQL error occurs -> ROLLBACK TRANSACTION
            if (conn != null) {
                try {
                    System.out.println("Transaction Error: Rolling back all changes... " + e.getMessage());
                    conn.rollback();
                } catch (SQLException exRoll) {
                    System.out.println("Rollback failed: " + exRoll.getMessage());
                }
            }
            throw new Exception("Order transaction failed and was rolled back: " + e.getMessage(), e);
        } finally {
            // STEP 9: Reset AutoCommit to true
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
