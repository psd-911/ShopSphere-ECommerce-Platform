# ShopSphere — Presentation Script & Viva Defense Notes
**Developed by:** Team `Code.exe`  
**Course:** Java Programming (Project-Based Evaluation)  
**File:** `ShopSphere_Architecture_Blueprint.pptx` (Located in this folder)  

---

### Slide 1: Title Slide
* **What to say:** *"Good morning/afternoon, Sir/Ma'am. Today, our team — Team 'Code.exe' — is presenting Review 1 of our Java Programming project titled 'ShopSphere — Desktop E-Commerce Management System' under Track A (Java GUI and JDBC). Our system is designed using Java Swing, SQLite JDBC, and Core Java principles."*

### Slide 2: Project Overview & Problem Statement
* **What to say:** *"In real-world e-commerce, three technical challenges are paramount: first, managing role-based workflows for buyers, sellers, and admins; second, handling concurrency issues like race conditions when multiple buyers buy limited flash-sale items simultaneously; and third, ensuring database consistency during order checkout so that inventory, payments, and orders are updated atomically."*

### Slide 3: System Architecture & Layered Design
* **What to say:** *"Our application follows a clean 3-tier architecture: the Presentation Layer built with Java Swing; the Business Logic Layer containing our OOP models, Generic Cart, and multithreading threads; and the Database Layer using SQLite via JDBC for persistent storage."*

### Slide 4: OOP Implementation (10 Marks)
* **What to say:** *"For OOP, we created two abstract base classes: User and Product. User is extended by Customer, Seller, and Admin. Product is extended by PhysicalProduct and DigitalProduct. We demonstrated Polymorphism through the abstract method calculateShippingFee(). For PhysicalProduct, shipping is calculated by weight (Rs. 40/kg), while for DigitalProduct, it returns Rs. 0 because digital goods are downloaded online. We also implemented a custom OutOfStockException and the Searchable interface."*

### Slide 5: Collections & Generics (6 Marks)
* **What to say:** *"In our shopping cart, we used Generics: Cart<T extends Product>, ensuring only valid product objects are added. We used three collections: HashMap to store each product and its quantity, ArrayList to extract items for display in the table, and HashSet to collect unique product categories without duplicates."*

### Slide 6: Multithreading & Synchronization (4 Marks)
* **What to say:** *"To demonstrate multithreading, we simulated a high-demand Flash Sale. When only 2 units are left in stock and 5 threads attempt to buy simultaneously, an unsynchronized program would cause a race condition and negative stock. We created FlashSaleThread extending Thread, and wrapped the purchase logic inside a synchronized method. When executed, exactly 2 threads succeed and 3 threads are rejected safely with zero overselling. We also have a live demonstration of this in Tab 5 of our GUI."*

### Slide 7: Database Design & Models (7 Marks)
* **What to say:** *"We designed normalized database tables: users, products, and orders. All database interactions are encapsulated inside DBHelper.java, keeping our user interface code separate from database queries."*

### Slide 8: JDBC Connectivity & CRUD (3 Marks)
* **What to say:** *"We connected to SQLite using standard JDBC. All our SQL queries use PreparedStatement with parameter placeholders ('?'). This guarantees high performance and completely eliminates SQL Injection vulnerabilities. We implemented complete CRUD: adding products, loading catalog, updating stock, and deleting products."*

### Slide 9: Transaction Management (3 Marks)
* **What to say:** *"For atomic checkout, we used JDBC Transaction Management. By calling conn.setAutoCommit(false), we execute stock checking, balance checking, order creation, stock decrement, and wallet balance deduction as one single atomic unit. If all succeed, we call conn.commit(). If any error occurs midway, conn.rollback() restores the database so no corrupted or partial data is saved."*

### Slide 10: Swing UI & User Experience
* **What to say:** *"Our Java Swing interface is user-friendly and divided into tabs: Product Catalog, Orders History, Seller Inventory, and Admin Directory. We also added 1-click quick login buttons to make it easy to demonstrate during viva."*

### Slide 11: Team Contributions (Team Code.exe)
* **What to say:** *"Our team divided the work equally across OOP models, generic collections, database and transaction management, and multithreading with Swing UI."*

### Slide 12: Summary & Next Steps
* **What to say:** *"In summary, all 6 requirements of Review 1 are 100% complete and verified by our automated test suite, earning 33/33 marks. For Review 2, we will add receipt PDF exports, enhanced unit tests, and additional features. Thank you, and Team Code.exe is ready for any questions!"*
