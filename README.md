# 🧑‍💻 User Service

The **User Service** is a Spring Boot microservice that manages users (citizens & admins).  
It provides APIs for:
- User registration & login
- Profile management
- Admin user management (CRUD, role change)

---

## 🚀 Features
- Citizen APIs:
  - Register new users
  - Login
  - Update & view profile
- Admin APIs:
  - Get all users (with pagination)
  - Get user by ID
  - Delete user
  - Change user role
- Global API response wrapper (`ApiResponse<T>`)
- Exception handling with unified error responses
- PostgreSQL integration
- Swagger UI for API docs

---

## 🛠️ Tech Stack
- **Java 17**
- **Spring Boot 3**
- Spring Web
- Spring Data JPA (Hibernate)
- PostgreSQL
- MapStruct (DTO mapping)
- Docker
- Swagger (OpenAPI)

---

## ⚙️ How to Run

### 1️⃣ Run with Maven (local)
```bash
mvn spring-boot:run
```
### 2️⃣ Run with Docker
- Build the image:
```bash
docker build -t user-service .
```
- Run container:
```bash
docker run -p 8082:8082 user-service
```

### 3️⃣ Run with Docker Compose
Recommended way (with PostgreSQL):
```bash
docker-compose up --build
```

---
## 🌍 API Endpoints

### 👤 Citizen Endpoints (/api/v1/citizen)
| Method | Endpoint        | Description          |
| ------ | --------------- | -------------------- |
| POST   | `/register`     | Register new user    |
| POST   | `/login`        | Login                |
| PUT    | `/profile/{id}` | Update profile by ID |
| GET    | `/profile/{id}` | Get profile by ID    |

---
### 👮 Admin Endpoints (/api/v1/admin/users)
| Method | Endpoint     | Description               |
| ------ | ------------ | ------------------------- |
| GET    | `/`          | Get all users (paginated) |
| GET    | `/{id}`      | Get user by ID            |
| DELETE | `/{id}`      | Delete user by ID         |
| PUT    | `/{id}/role` | Change user role          |

---
## 📖 Example Requests (Postman)

### ➡️ Register
```bash
POST /api/v1/citizen/register
Content-Type: application/json

{
  "fullName": "Ali Ahmed",
  "email": "ali@example.com",
  "password": "Password123",
  "phone": "01012345678",
  "nationalId": "12345678901234",
  "dateOfBirth": "1995-05-15",
  "address": "Cairo, Egypt"
}
```

### ➡️ Login
```bash
POST /api/v1/citizen/login
Content-Type: application/json

{
  "email": "ali@example.com",
  "password": "Password123"
}
```

### ➡️ Update Role (Admin)
```bash
PUT /api/v1/admin/users/{id}/role
Content-Type: application/json

{
  "role": "ADMIN"
}
```

---
## 🐘 Database (PostgreSQL)
Default database config (edit in application.yml if needed):
```bash
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/userdb
    username: user
    password: password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```
You can view tables & data using pgAdmin.

---
## 📦 Project Structure
```bash
user-service/
 ┣ src/main/java/ghaidaa/com/user_service
 ┃ ┣ controllers/        # REST Controllers
 ┃ ┣ dtos/              # Request & Response DTOs
 ┃ ┣ entities/          # JPA Entities
 ┃ ┣ enums/             # Role, Status enums
 ┃ ┣ exceptions/        # Custom exceptions + handler
 ┃ ┣ mappers/           # MapStruct interfaces
 ┃ ┣ repositories/      # JPA Repositories
 ┃ ┣ services/          # Service interfaces & impls
 ┃ ┗ UserServiceApp.java # Main class
 ┣ src/main/resources/
 ┃ ┣ application.yml     # Config
 ┗ pom.xml
```
