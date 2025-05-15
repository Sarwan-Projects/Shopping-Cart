# 🛒 Shopping Cart Application

A full-featured, web-based Shopping Cart application built with **Spring Boot**, **Thymeleaf**, and **MySQL**. It provides a user-friendly interface for customers to browse products, manage their shopping carts, and place orders. An admin dashboard is also included for managing products, categories, and user accounts.

---

## 🚀 Features

- ✅ **User Registration & Authentication**  
  Secure login, registration, and profile management using Spring Security.

- 🛍️ **Product Management**  
  Admins can create, edit, and delete products with image support.

- 🗂️ **Category Management**  
  Organize products by categories via the admin interface.

- 🛒 **Shopping Cart Functionality**  
  Add items to cart, adjust quantities, and complete checkout.

- 📦 **Order Management**  
  Users can view order history and track current orders.

- 📱 **Responsive Design**  
  Built using Bootstrap for mobile-friendly, modern UI.

---

## 🧰 Tech Stack

### 💻 Backend
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL

### 🎨 Frontend
- Thymeleaf
- Bootstrap
- jQuery

### 🛠️ Development Tools
- Maven
- Lombok

---

## ⚙️ Installation & Setup

### ✅ Prerequisites
- Java 17+
- Maven
- MySQL Server

---

🛠️ Set Up MySQL Database
Create a database named shopping_cart (or your preferred name).

Update your application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password

🔧 Build the Application
mvn clean install

▶️ Run the Application
mvn spring-boot:run

🧪 Usage
👤 User
Register a new account

Login using your credentials

Browse and search products

Add to cart and checkout

View order history

🔐 Admin
Log in with admin credentials

Manage products, categories, and users via admin panel

### 📥 Clone the Repository

```bash
git clone https://github.com/yourusername/ShoppingCart.git
cd ShoppingCart
