<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.4.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/MySQL-8.0.35-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/>
  <img src="https://img.shields.io/badge/Bootstrap-5.3.3-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white"/>
  <img src="https://img.shields.io/badge/Thymeleaf-3.x-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white"/>
  <img src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge"/>
</p>

<h1 align="center">🛒 Ecom Store</h1>
<p align="center"><strong>Modern Full-Stack E-Commerce Platform with SEO-Friendly URLs</strong></p>
<p align="center">
  <a href="https://shopping-cart-hikn.onrender.com">🌐 Live Demo</a> •
  <a href="#-test-credentials">🔑 Test Login</a> •
  <a href="#-features">✨ Features</a> •
  <a href="#-api-endpoints">📡 API</a>
</p>

---

## 🌐 Live Demo

**[https://shopping-cart-hikn.onrender.com](https://shopping-cart-hikn.onrender.com)**

---

## 🔑 Test Credentials

| Role | Email | Password |
|------|-------|----------|
| **Admin** | `admin@ecom.com` | `admin123` |
| **User** | `user@ecom.com` | `user123` |

> **Sample Data:** 6 categories with 36 products are auto-initialized on first deployment.

---

## ✨ Features

### Customer Features
- 🔐 Secure registration & authentication
- 🔍 Product search & category filtering
- 🛒 Shopping cart management
- 📦 Order placement & tracking
- 👤 Profile & password management
- 📧 Password reset via email

### Admin Features
- 📊 Admin dashboard
- 📦 Product CRUD operations
- 🏷️ Category management
- 📋 Order status management
- 👥 User management & status control
- 👨‍💼 Admin account creation

### Technical Highlights
- 🔗 SEO-friendly URLs with product slugs
- 🎨 Modern dark theme UI with responsive design
- 🔒 Spring Security authentication
- 📱 Mobile-first responsive layout
- ⚡ HikariCP connection pooling
- 🖼️ Optimized image handling

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| **Backend** | Spring Boot 3.4.1, Spring Security 6, Spring Data JPA |
| **Database** | MySQL 8.0.35 (Aiven Cloud), HikariCP Connection Pool |
| **Frontend** | Thymeleaf 3.x, Bootstrap 5.3.3, Font Awesome 6.5.1 |
| **Build** | Maven, Java 17 |
| **Deployment** | Docker, Render.com |

---

## 📁 Project Structure

```
src/main/java/com/ecom/
├── config/          # Security, DataInitializer
├── controller/      # HomeController, UserController, AdminController
├── model/           # Entity classes (Product, Category, User, Cart, Order)
├── repository/      # JPA repositories
├── service/         # Business logic interfaces & implementations
└── util/            # Utilities, constants, helpers

src/main/resources/
├── static/
│   ├── css/         # Custom styles
│   ├── js/          # JavaScript
│   └── img/         # Product, category, profile images
├── templates/
│   ├── admin/       # Admin panel templates
│   ├── user/        # User dashboard templates
│   └── error/       # Custom error pages (404, 500)
└── application.properties
```

---

## 📡 API Endpoints

### Public Routes
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Home page with featured products |
| GET | `/products` | Product listing with pagination |
| GET | `/products?category={name}` | Filter by category |
| GET | `/product/{slug}` | Product details (SEO-friendly) |
| GET | `/search?ch={query}` | Search products |
| GET | `/signin` | Login page |
| GET | `/register` | Registration page |
| POST | `/saveUser` | User registration |
| GET | `/forgot-password` | Password reset request |
| POST | `/reset-password` | Password reset |

### User Routes (`/user/*`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/user/` | User dashboard |
| GET | `/user/cart` | View shopping cart |
| GET | `/user/addCart?pid={id}&uid={id}` | Add product to cart |
| GET | `/user/cartQuantity?sy={+/-}&cid={id}` | Update cart quantity |
| GET | `/user/orders` | Checkout page |
| POST | `/user/save-order` | Place order |
| GET | `/user/user-orders` | Order history |
| GET | `/user/update-status?id={id}&st={status}` | Cancel order |
| GET | `/user/profile` | User profile |
| POST | `/user/update-profile` | Update profile |
| POST | `/user/change-password` | Change password |

### Admin Routes (`/admin/*`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/admin/` | Admin dashboard |
| GET | `/admin/products` | Product management |
| GET | `/admin/add-product` | Add product form |
| POST | `/admin/saveProduct` | Save new product |
| GET | `/admin/product/edit/{id}` | Edit product |
| POST | `/admin/updateProduct` | Update product |
| GET | `/admin/product/delete/{id}` | Delete product |
| GET | `/admin/category` | Category management |
| POST | `/admin/saveCategory` | Save category |
| GET | `/admin/category/edit/{id}` | Edit category |
| POST | `/admin/updateCategory` | Update category |
| GET | `/admin/category/delete/{id}` | Delete category |
| GET | `/admin/orders` | Order management |
| POST | `/admin/update-order-status` | Update order status |
| GET | `/admin/users?type={1\|2}` | User/Admin list |
| GET | `/admin/updateStatus` | Toggle user status |
| GET | `/admin/add-admin` | Add admin form |
| POST | `/admin/save-admin` | Create admin |
| GET | `/admin/profile` | Admin profile |
| POST | `/admin/update-profile` | Update profile |
| POST | `/admin/change-password` | Change password |

---

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8.0+

### Local Development

```bash
# Clone repository
git clone https://github.com/Sarwan-Projects/Shopping-Cart.git
cd Shopping-Cart

# Set environment variables (Windows CMD)
set DB_URL=jdbc:mysql://localhost:3306/ecom_db
set DB_USERNAME=root
set DB_PASSWORD=your_password
set MAIL_USERNAME=your_email@gmail.com
set MAIL_PASSWORD=your_app_password

# Run application
mvnw.cmd spring-boot:run
```

Access at: `http://localhost:8080`

---

## ☁️ Deployment

### Environment Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `DB_URL` | MySQL JDBC URL with SSL | `jdbc:mysql://host:port/db?sslMode=REQUIRED` |
| `DB_USERNAME` | Database username | `avnadmin` |
| `DB_PASSWORD` | Database password | `***` |
| `MAIL_USERNAME` | Gmail address for notifications | `app@gmail.com` |
| `MAIL_PASSWORD` | Gmail app password | `***` |

### Recommended Platforms

| Service | Type | Free Tier |
|---------|------|-----------|
| [Render.com](https://render.com) | App Hosting | ✅ 750 hrs/month |
| [Aiven.io](https://aiven.io) | MySQL Database | ✅ Free tier |
| [Railway.app](https://railway.app) | Full Stack | ✅ $5 credit |

---

## 📄 License

This project is licensed under the MIT License.

---

<p align="center">
  <strong>Built with ❤️ by <a href="https://github.com/Sarwan-Projects">Sarwan Chhetri</a></strong>
</p>
