# Ecommerce Backend System

A production-ready Ecommerce Backend System built using Spring Boot for managing products, categories, users, carts, orders, payments, and authentication.

This project was designed with a strong focus on backend engineering principles such as clean architecture, stateless authentication, caching, secure payment processing, scalable API design, and cloud-based media management.

---

# Live API

**Base URL**

https://ecommerce-backend-jojn.onrender.com

> Note: The backend may take up to a minute to wake up due to Render free-tier cold starts.

---

# Swagger Documentation

https://ecommerce-backend-jojn.onrender.com/swagger-ui/index.html

---

# Frontend

https://ecommerce-frontend-rosy-pi.vercel.app/

---

# Features

## Authentication & Authorization

- JWT Access Token Authentication
- Refresh Token Authentication
- Refresh Token Rotation
- Multi-session Login Support
- Logout Current Session
- Logout All Sessions
- Role-Based Access Control
- Spring Security Integration
- Protected Endpoints

---

## Product Management

- Create Products
- Update Products
- Delete Products
- View Products
- Search Products
- Pagination Support
- Sorting Support
- Category Filtering
- Product Image Upload
- Product Image Update
- Product Image Deletion

---

## Category Management

- Create Categories
- Update Categories
- Delete Categories
- View Categories
- Product-Category Relationship

---

## Cart Management

- Add Products to Cart
- Update Cart Quantity
- Remove Products from Cart
- View User Cart
- Automatic Cart Total Calculation

---

## Order Management

- Place Orders
- View User Orders
- Order History Tracking
- Order Item Management
- Order Status Handling

---

## Payment Integration

- Razorpay Order Creation
- Secure Payment Verification
- Signature Validation
- Payment Status Management

---

## User Profile Management

- View Profile
- Update Profile Details
- Upload Profile Image
- Delete Profile Image
- Date of Birth Support
- Gender Support

---

## Cloud Media Storage

- Cloudinary Integration
- Product Image Storage
- Profile Image Storage
- Automatic Image Cleanup
- Public URL Management

---

## Caching

- Redis Integration
- Product Caching
- Category Caching
- Improved API Performance

---

## API Design

- RESTful API Design
- DTO-Based Architecture
- Validation Support
- Global Exception Handling
- Standardized API Responses
- Proper HTTP Status Codes

---

# Security Features

- JWT Authentication
- Refresh Token Authentication
- Refresh Token Rotation
- Role-Based Authorization
- BCrypt Password Hashing
- Stateless Security Architecture
- Protected Endpoints
- Secure Session Management

---

# Persistence & Performance

- Spring Data JPA
- Hibernate ORM
- PostgreSQL Database
- Optimized Entity Relationships
- Lazy Loading
- Pagination Support
- Sorting Support
- Redis Caching

---

# Architecture

Layered Architecture

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Project Structure

```text
src/main/java
│
├── controller
├── service
├── repository
├── entity
├── dto
├── security
├── config
├── exception
├── cache
├── util
```

---

# Tech Stack

## Backend

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- Maven

## Database

- PostgreSQL

## Authentication

- JWT
- Refresh Tokens

## Caching

- Redis

## Payment Gateway

- Razorpay

## Cloud Storage

- Cloudinary

## Documentation

- Swagger / OpenAPI

## Containerization

- Docker
- Docker Compose

---

# API Examples

## Product Pagination

```http
GET /products?page=0&size=10&sortBy=name&direction=asc
```

## Login

```http
POST /auth/login
```

## Refresh Token

```http
POST /auth/refresh-token
```

## Authenticated Request

```http
Authorization: Bearer <ACCESS_TOKEN>
```

---

# Running the Project Locally

## Clone Repository

```bash
git clone https://github.com/naveendp2805/ecommerce-backend.git
```

## Configure Environment Variables

Configure the following environment variables:

```properties
DATABASE_URL=
DATABASE_USERNAME=
DATABASE_PASSWORD=

JWT_SECRET=
JWT_EXPIRATION=

REFRESH_TOKEN_EXPIRATION=

REDIS_HOST=
REDIS_PORT=

RAZORPAY_KEY_ID=
RAZORPAY_KEY_SECRET=

CLOUDINARY_CLOUD_NAME=
CLOUDINARY_API_KEY=
CLOUDINARY_API_SECRET=
```

---

## Build Project

```bash
mvn clean install
```

---

## Run Application

```bash
mvn spring-boot:run
```

Application runs on:

```text
http://localhost:8080
```

---

# Running with Docker

This project can be run using Docker and Docker Compose.

## Prerequisites

Make sure the following are installed:

- Docker
- Docker Compose

Verify installation:

```bash
docker --version
docker compose version
```

---

## Build Docker Image

```bash
docker build -t ecommerce-backend .
```

---

## Run Docker Container

```bash
docker run -p 8080:8080 ecommerce-backend
```

---

## Run with Docker Compose

```bash
docker compose up -d
```

This starts:

- Spring Boot Application
- PostgreSQL
- Redis

---

## View Running Containers

```bash
docker ps
```

---

## View Container Logs

```bash
docker logs <container_id>
```

---

## Stop Container

```bash
docker stop <container_id>
```

---

## Remove Container

```bash
docker rm <container_id>
```

---

## Remove Image

```bash
docker rmi ecommerce-backend
```

---

# Swagger UI

Access API documentation:

https://ecommerce-backend-jojn.onrender.com/swagger-ui/index.html

---

# Deployment

The application is deployed using:

- Render (Backend)
- PostgreSQL Database
- Redis Cache
- Cloudinary Media Storage
- Razorpay Payment Gateway
- Vercel (Frontend)

---

# Future Improvements

- Product Reviews & Ratings
- Wishlist Module
- Inventory Management
- Admin Analytics Dashboard
- Email Notifications
- Elasticsearch Integration
- CI/CD Pipeline
- Kubernetes Deployment
- Microservices Migration

---

# Learning Outcomes

This project helped strengthen understanding of:

- Spring Boot Architecture
- Spring Security
- JWT Authentication
- Refresh Token Rotation
- Redis Caching
- Cloudinary Integration
- Razorpay Payment Workflow
- Docker & Docker Compose
- REST API Design
- Hibernate & JPA
- Database Design
- Transaction Management
- Scalable Backend Architecture
- Production Deployment

---

# Author

## Naveen Durga Prasad

Backend-focused developer interested in:

- Java Backend Engineering
- Spring Ecosystem
- Distributed Systems
- Cloud Technologies
- Scalable Architectures
- System Design

---

If you found this project useful, feel free to star the repository.
