

# 🛒 Order Management API (Spring Boot + React + JWT)

This is a Fullstack application with a **Spring Boot (Java 21)** backend and a **React** frontend (frontend not included in this repository). The API focuses on user authentication and CRUD operations for a product catalog.

---

## 🚀 How to Run the Application

### 1. Prerequisites
- Java 17+
- Maven 3.8+
- A configured database (MySQL8)
- (Optional) Postman or any HTTP client
- (Optional) React frontend running at `http://localhost:3000`

---
When the application starts for the first time, it checks if a default user exists:

Email: test@gmail.com

Password: 12345678

 API Documentation (Swagger)
After the application is running, open the following URL in your browser to explore all available endpoints:

👉 http://localhost:8081/swagger-ui/index.html#/

The Swagger UI provides detailed documentation and testing interface for:

🔐 Authentication
Register new user

Login (returns JWT access and refresh tokens)

Logout

👥 Users
Create new users

Fetch user list (with pagination)

Update user details

Delete users

📦 Products
Insert new products

List products (paginated)

Update product data

Delete products

All protected endpoints require a valid JWT token in the Authorization header.

🔐 JWT Authentication
Once logged in, you'll receive a JWT token. Use it to access secured endpoints by passing it in the Authorization header:

http
Copiar
Editar
