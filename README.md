<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.4.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/MySQL-8.4-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/>
  <img src="https://img.shields.io/badge/Bootstrap-5.3.3-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white"/>
  <img src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge"/>
</p>

<h1 align="center">🛒 Ecom Store</h1>
<p align="center"><strong>Modern Full-Stack E-Commerce Platform</strong></p>
<p align="center">A production-ready online shopping application with user authentication, product catalog, shopping cart, order management, and admin dashboard.</p>

---

## 📋 Table of Contents
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [API Endpoints](#-api-endpoints)
- [Getting Started](#-getting-started)
- [Deployment](#-deployment)
- [Environment Variables](#-environment-variables)

---

## ✨ Features

<table>
<tr>
<td width="50%" valign="top">

### 🛍️ Customer Portal
- User registration with email verification
- Secure login with Spring Security
- Product browsing with category filters
- Full-text product search
- Shopping cart with quantity management
- Checkout with billing address
- Order history and tracking
- Profile and password management

</td>
<td width="50%" valign="top">

### ⚙️ Admin Dashboard
- Centralized admin panel
- Product CRUD with image upload
- Category management
- Order status workflow
- User activation/deactivation
- Multi-admin support
- Sales overview

</td>
</tr>
</table>

---

## 🛠️ Tech Stack

| Category | Technologies |
|----------|-------------|
| **Backend** | Spring Boot 3.4.1 • Spring Security 6.4 • Spring Data JPA • Hibernate 6.6 |
| **Database** | MySQL 8.4 • HikariCP Connection Pool |
| **Frontend** | Thymeleaf 3.1 • Bootstrap 5.3.3 • Font Awesome 6.5.1 • jQuery 3.7.1 |
| **Build** | Maven • Java 17 LTS |
| **Security** | BCrypt Encryption • CSRF Protection • Role-Based Access |

---

## 📁 Project Structure

```
Shopping-Cart/
├── 📂 src/main/java/com/ecom/
│   ├── 📂 config/                    # Security & application config
│   │   ├── SecurityConfig.java       # Spring Security configuration
│   │   ├── AuthSuccessHandlerImpl.java
│   │   ├── AuthFailureHandlerImpl.java
│   │   ├── CustomUser.java
│   │   └── UserDetailsServiceImpl.java
│   │
│   ├── 📂 controller/                # MVC Controllers
│   │   ├── HomeController.java       # Public pages (/, /products, /login)
│   │   ├── UserController.java       # User operations (/user/**)
│   │   └── AdminController.java      # Admin operations (/admin/**)
│   │
│   ├── 📂 model/                     # JPA Entities
│   │   ├── UserDtls.java             # User entity
│   │   ├── Product.java              # Product entity
│   │   ├── Category.java             # Category entity
│   │   ├── Cart.java                 # Shopping cart entity
│   │   ├── ProductOrder.java         # Order entity
│   │   └── BillingAddress.java       # Billing details
│   │
│   ├── 📂 repository/                # Data Access Layer
│   │   ├── UserRepository.java
│   │   ├── ProductRepository.java
│   │   ├── CategoryRepo.java
│   │   ├── CartRepository.java
│   │   └── ProductOrderRepository.java
│   │
│   ├── 📂 service/                   # Business Logic
│   │   ├── UserService.java
│   │   ├── ProductService.java
│   │   ├── CategoryService.java
│   │   ├── CartService.java
│   │   ├── OrderService.java
│   │   └── 📂 impl/                  # Service implementations
│   │
│   ├── 📂 util/                      # Utilities
│   │   ├── CommonUtil.java           # Email & helper methods
│   │   ├── OrderStatus.java          # Order status enum
│   │   └── AppConstant.java
│   │
│   └── ShoppingCartApplication.java  # Main application class
│
├── 📂 src/main/resources/
│   ├── 📂 static/
│   │   ├── 📂 css/style.css          # Custom styles
│   │   ├── 📂 js/script.js           # Form validation
│   │   └── 📂 img/                   # Product, category, profile images
│   │
│   ├── 📂 templates/
│   │   ├── base.html                 # Layout template
│   │   ├── index.html                # Home page
│   │   ├── login.html                # Login page
│   │   ├── register.html             # Registration
│   │   ├── product.html              # Product listing
│   │   ├── view_products.html        # Product details
│   │   ├── 📂 user/                  # User pages (cart, orders, profile)
│   │   └── 📂 admin/                 # Admin pages (dashboard, products, orders)
│   │
│   └── application.properties        # Application configuration
│
├── pom.xml                           # Maven dependencies
└── README.md
```

---

## 🔌 API Endpoints

### 🌐 Public Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/` | Home page with featured products |
| `GET` | `/products` | Product listing with pagination |
| `GET` | `/products?category={name}` | Filter by category |
| `GET` | `/products?ch={query}` | Search products |
| `GET` | `/product/{id}` | Product detail page |
| `GET` | `/signin` | Login page |
| `GET` | `/register` | Registration page |
| `POST` | `/saveUser` | Register new user |
| `GET` | `/forgot-password` | Password reset request |
| `POST` | `/reset-password` | Reset password |

### 👤 User Endpoints (`/user/**`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/user/cart` | View shopping cart |
| `GET` | `/user/addCart?pid={id}&uid={id}` | Add product to cart |
| `GET` | `/user/cartQuantity?sy={in/de}&cid={id}` | Update quantity |
| `GET` | `/user/orders` | Checkout page |
| `POST` | `/user/save-order` | Place order |
| `GET` | `/user/user-orders` | Order history |
| `GET` | `/user/update-status?id={id}&st={status}` | Cancel order |
| `GET` | `/user/profile` | User profile |
| `POST` | `/user/update-profile` | Update profile |
| `POST` | `/user/change-password` | Change password |

### 🔐 Admin Endpoints (`/admin/**`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/admin/` | Admin dashboard |
| `GET` | `/admin/products` | Product management |
| `GET` | `/admin/add-product` | Add product form |
| `POST` | `/admin/saveProduct` | Save new product |
| `GET` | `/admin/editProduct/{id}` | Edit product form |
| `POST` | `/admin/updateProduct` | Update product |
| `GET` | `/admin/deleteProduct/{id}` | Delete product |
| `GET` | `/admin/category` | Category management |
| `POST` | `/admin/saveCategory` | Save category |
| `GET` | `/admin/orders` | Order management |
| `POST` | `/admin/update-order-status` | Update order status |
| `GET` | `/admin/users?type={1/2}` | User/Admin list |
| `GET` | `/admin/updateStatus?status={bool}&id={id}` | Toggle user status |

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.x

### Installation

```bash
# Clone repository
git clone https://github.com/Sarwan-Projects/Shopping-Cart.git
cd Shopping-Cart

# Set environment variables (Windows)
set DB_URL=jdbc:mysql://localhost:3306/shoppingcart
set DB_USERNAME=root
set DB_PASSWORD=your_password
set MAIL_USERNAME=your@gmail.com
set MAIL_PASSWORD=your_app_password

# Run application
./mvnw spring-boot:run
```

Open **http://localhost:8080**

---

## ☁️ Deployment

### Free Hosting Options

| Platform | Free Tier | Deploy |
|----------|-----------|--------|
| [Render](https://render.com) | 750 hrs/month | Build: `./mvnw clean package -DskipTests` |
| [Railway](https://railway.app) | $5 credit | Auto-detect Spring Boot |
| [Koyeb](https://koyeb.com) | 2 nano instances | Buildpack deployment |

### Database (Free)
- [Aiven](https://aiven.io) - MySQL free tier with SSL

---

## 🔐 Environment Variables

| Variable | Required | Description |
|----------|----------|-------------|
| `DB_URL` | ✅ | JDBC connection URL |
| `DB_USERNAME` | ✅ | Database username |
| `DB_PASSWORD` | ✅ | Database password |
| `MAIL_USERNAME` | ✅ | Gmail address |
| `MAIL_PASSWORD` | ✅ | Gmail app password |
| `PORT` | ❌ | Server port (default: 8080) |

---

## 📄 License

This project is licensed under the MIT License.

---

<p align="center">
  <strong>Built with ❤️ by <a href="https://github.com/Sarwan-Projects">Sarwan Chhetri</a></strong>
</p>
