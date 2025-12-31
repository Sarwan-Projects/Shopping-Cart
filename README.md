<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.4.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/MySQL-8.4-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/>
  <img src="https://img.shields.io/badge/Bootstrap-5.3.3-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white"/>
  <img src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge"/>
</p>

<h1 align="center">🛒 Ecom Store</h1>
<p align="center"><strong>Modern Full-Stack E-Commerce Platform</strong></p>
<p align="center">
  <a href="https://shopping-cart-hikn.onrender.com">🌐 Live Demo</a> •
  <a href="#-test-credentials">🔑 Test Login</a> •
  <a href="#-features">✨ Features</a>
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

> Admin can manage products, categories, orders, and users. User can browse, add to cart, and place orders.

---

## ✨ Features

| Customer | Admin |
|----------|-------|
| User registration & login | Dashboard |
| Browse & search products | Product management |
| Shopping cart | Category management |
| Order tracking | Order management |
| Profile management | User management |
| Password reset | Admin creation |

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| Backend | Spring Boot 3.4.1, Spring Security, JPA |
| Database | MySQL 8.4, HikariCP |
| Frontend | Thymeleaf, Bootstrap 5.3.3 |
| Build | Maven, Java 17 |

---

## 📁 Project Structure

```
src/main/java/com/ecom/
├── config/          # Security & data initialization
├── controller/      # HomeController, UserController, AdminController
├── model/           # UserDtls, Product, Category, Cart, ProductOrder
├── repository/      # JPA repositories
├── service/         # Business logic layer
└── util/            # Utilities & constants

src/main/resources/
├── static/          # CSS, JS, images
├── templates/       # Thymeleaf templates (admin/, user/)
└── application.properties
```

---

## 🔌 API Endpoints

### Public
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Home page |
| GET | `/products` | Product listing |
| GET | `/product/{id}` | Product details |
| GET | `/signin` | Login |
| GET | `/register` | Registration |

### User (`/user/**`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/user/cart` | Shopping cart |
| GET | `/user/addCart` | Add to cart |
| POST | `/user/save-order` | Place order |
| GET | `/user/user-orders` | Order history |

### Admin (`/admin/**`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/admin/` | Dashboard |
| GET | `/admin/products` | Manage products |
| GET | `/admin/category` | Manage categories |
| GET | `/admin/orders` | Manage orders |
| GET | `/admin/users` | Manage users |

---

## 🚀 Quick Start

```bash
git clone https://github.com/Sarwan-Projects/Shopping-Cart.git
cd Shopping-Cart

# Set environment variables
set DB_URL=jdbc:mysql://host:port/database?sslMode=REQUIRED
set DB_USERNAME=username
set DB_PASSWORD=password
set MAIL_USERNAME=email@gmail.com
set MAIL_PASSWORD=app_password

./mvnw spring-boot:run
```

---

## ☁️ Deployment

### Environment Variables
| Variable | Description |
|----------|-------------|
| `DB_URL` | MySQL JDBC URL |
| `DB_USERNAME` | Database user |
| `DB_PASSWORD` | Database password |
| `MAIL_USERNAME` | Gmail address |
| `MAIL_PASSWORD` | Gmail app password |

### Free Hosting
- **Render.com** - Docker deployment
- **Railway.app** - Auto-detect Spring Boot
- **Koyeb.com** - Buildpack deployment

### Free Database
- **Aiven.io** - MySQL free tier with SSL

---

## 📄 License

MIT License

---

<p align="center">
  <strong>Built with ❤️ by <a href="https://github.com/Sarwan-Projects">Sarwan Chhetri</a></strong>
</p>
