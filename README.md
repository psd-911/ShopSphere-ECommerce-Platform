# ShopSphere — Desktop E-Commerce Management System
> **Developed by Team:** `Code.exe`  
> **Subject:** Java Programming (Project-Based Evaluation)  
> **Course Track:** Java GUI Based Projects (Rubric A)  
> **Batch:** 2029 Passout | Galgotias University & GUVI / HCL  
> **Target Evaluation:** Review 1 Pre-Midterm Submission (33 / 33 Marks)  

---

## 📌 Project Overview
**ShopSphere** is a lightweight, multi-tier desktop E-Commerce platform built in Core Java using **Java Swing** for the graphical user interface and **SQLite** via **JDBC** for local database persistence.

The project incorporates 3 distinct role-based portals:
1. **Customer:** Browse products, filter by category/keyword, manage a generic shopping cart, and place orders with atomic ACID checkout.
2. **Seller:** Add new products (Physical goods with weight-based courier charges vs. Digital goods with instant download links) and update inventory.
3. **Admin:** Access user records and oversee platform-wide orders.

The application also includes an interactive **Multithreading & Synchronization Test Bench** to demonstrate thread safety and prevention of race conditions during concurrent flash sales.

---

## 👥 Team Details — `Code.exe`

| Role in Team | Responsibilities & Module Ownership |
| :--- | :--- |
| **Team Leader (Team Code.exe)** | Abstract User hierarchy (`User`, `Customer`, `Seller`, `Admin`), login dialog, and role permissions. |
| **Team Member 2 (Team Code.exe)** | Product hierarchy (`Product`, `PhysicalProduct`, `DigitalProduct`), and `Cart<T>` Generic collection. |
| **Team Member 3 (Team Code.exe)** | SQLite database schema, `DBHelper.java` CRUD operations, and ACID transaction checkout. |
| **Team Member 4 (Team Code.exe)** | `FlashSaleThread` multithreading with `synchronized` lock, Swing UI layout, and automated rubric test suite. |

---

## 🏆 Review 1 Rubric Score Sheet (33 / 33 Marks)

| Deliverable Title | Allocated Marks | Source Files | Implementation Details |
| :--- | :---: | :--- | :--- |
| **1. OOP Implementation** | **10 Marks** | `User.java`, `Customer.java`, `Seller.java`, `Admin.java`, `Product.java`, `PhysicalProduct.java`, `DigitalProduct.java`, `Searchable.java`, `OutOfStockException.java` | • **Abstraction & Inheritance:** Base abstract classes `User` and `Product`.<br>• **Polymorphism:** `calculateShippingFee(qty)` overridden across `PhysicalProduct` (Rs. 40/kg) and `DigitalProduct` (Rs. 0.00).<br>• **Interfaces:** `Searchable` with `matches(keyword)`.<br>• **Custom Exception:** `OutOfStockException`. |
| **2. Collections & Generics** | **6 Marks** | `Cart.java` | • **Generics:** `Cart<T extends Product>`.<br>• **Map:** `HashMap<T, Integer>` mapping products to quantities.<br>• **List:** `ArrayList<T>` for distinct item listing.<br>• **Set:** `HashSet<String>` for unique product categories.<br>• Standard loops for subtotal and grand total. |
| **3. Multithreading & Synchronization** | **4 Marks** | `FlashSaleThread.java`, `ShopApp.java` (Tab 5) | • **Thread Class:** `FlashSaleThread extends Thread`.<br>• **Synchronization:** `public static synchronized boolean buyProductSynchronized(...)`.<br>• **Live Proof:** 5 concurrent threads compete for 2 items: exactly 2 succeed and 3 get blocked without negative overselling! |
| **4. Database Models & Helper** | **7 Marks** | `Product.java`, `User.java`, `Order.java`, `DBHelper.java` | • Clean entity models matching database tables (`users`, `products`, `orders`).<br>• Encapsulated persistence handling in `DBHelper.java`. |
| **5. JDBC Connectivity (CRUD)** | **3 Marks** | `DBHelper.java` | • Centralized singleton JDBC connection (`jdbc:sqlite:shopsphere.db`).<br>• 100% parameterized `PreparedStatement` across Create, Read, Update, Delete queries. |
| **6. JDBC Transaction Management** | **3 Marks** | `DBHelper.java` (`placeOrderTransaction`) | • **Atomic Checkout:** `conn.setAutoCommit(false);`<br>• Multi-step verification: stock check, balance check, order insertion, stock update, wallet deduction.<br>• `conn.commit()` on success, `conn.rollback()` on exception. |
| **TOTAL** | **33 / 33** | | **All 6 tests verified by automated test suite.** |

---

## 💻 Tech Stack & Requirements
* **Language:** Java SE 8 or higher (Java 17 / 21 / 26 tested)
* **GUI Toolkit:** Java Swing (`JFrame`, `JTabbedPane`, `JTable`, `JOptionPane`)
* **Database:** SQLite 3 (Driver included in `lib/`, zero installation required)
* **Build System:** Standard `javac` command line or simple IDE (VS Code, Eclipse, IntelliJ, NetBeans)

---

## 📁 Repository Structure

```text
review one/
├── README.md                            <-- Project documentation & Rubric guide
├── ShopSphere_Architecture_Blueprint.pptx <-- Official presentation deck (by Team Code.exe)
├── PRESENTATION_NOTES.md                <-- Speaker notes & viva presentation script
├── compile.bat                          <-- One-click batch script to compile Java code
├── run.bat                              <-- One-click batch script to launch the GUI app
├── test_review1.bat                     <-- One-click batch script to run automated test suite
├── lib/                                 <-- SQLite JDBC driver & SLF4J libraries
│   ├── sqlite-jdbc-3.45.2.0.jar
│   ├── slf4j-api-2.0.12.jar
│   └── slf4j-simple-2.0.12.jar
└── src/com/shopsphere/
    ├── App.java                         <-- Main entry point
    ├── ShopApp.java                     <-- Swing GUI (Catalog, Cart, Orders, Seller, Threads)
    ├── User.java                        <-- Abstract User base class
    ├── Customer.java                    <-- Customer subclass
    ├── Seller.java                      <-- Seller subclass
    ├── Admin.java                       <-- Admin subclass
    ├── Product.java                     <-- Abstract Product base class
    ├── PhysicalProduct.java             <-- Weight-based delivery child class
    ├── DigitalProduct.java              <-- Instant download child class
    ├── Searchable.java                  <-- Search interface
    ├── OutOfStockException.java         <-- Custom exception class
    ├── Cart.java                        <-- Generic Cart<T> with HashMap, ArrayList, HashSet
    ├── DBHelper.java                    <-- JDBC queries, CRUD, and ACID transactions
    ├── FlashSaleThread.java             <-- Thread class with synchronized method
    ├── Order.java                       <-- Order entity model
    └── Review1Test.java                 <-- Automated 6-part test runner
```

---

## 🚀 How to Run the Project

### Option A: Using Batch Files (Windows)
1. **Compile:** Double-click `compile.bat`
2. **Run GUI App:** Double-click `run.bat`
3. **Run 33-Mark Rubric Test:** Double-click `test_review1.bat`

### Option B: Using Terminal / Command Prompt
1. Navigate to the project directory:
   ```cmd
   cd "review one"
   ```
2. Compile:
   ```cmd
   javac -d bin -cp "lib/*" src/com/shopsphere/*.java
   ```
3. Run the GUI Application:
   ```cmd
   java --enable-native-access=ALL-UNNAMED -cp "bin;lib/*" com.shopsphere.App
   ```
4. Run the Rubric Verification Test:
   ```cmd
   java --enable-native-access=ALL-UNNAMED -cp "bin;lib/*" com.shopsphere.Review1Test
   ```

---

## 🔑 Demo Credentials (For Evaluation)

The app features convenient 1-click login buttons on the login dialog:
* **Customer:** Username: `customer` | Password: `buyer123` (Initial balance: Rs. 25,000)
* **Seller:** Username: `techstore` | Password: `seller123` (Can add products and update stock)
* **Admin:** Username: `admin` | Password: `admin123` (Full system access)
