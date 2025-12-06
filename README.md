# Todo Management API 🚀

A professional, production-ready Todo Management REST API built with Spring Boot, featuring JWT authentication, role-based authorization, and database migrations.

## ✨ Features

- 🔐 **JWT-based Authentication** - Secure token-based authentication
- 👥 **Role-based Authorization** - USER and ADMIN roles with different permissions
- ✅ **Full CRUD Operations** - Complete task management functionality
- 🗄️ **Database Migrations** - Flyway for version-controlled database schema
- 🔄 **Auto-admin Setup** - Default admin user on first run
- ⚡ **Production Ready** - Environment variable configuration, secure defaults
- 📦 **Maven Build** - Standard Java project structure

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/todo-management-api.git
   cd todo-management-api
   ```

2. **Create MySQL database**
   ```sql
   CREATE DATABASE To_Do;
   ```

3. **Configure the application**
   ```bash
   # Copy the example configuration
   cp application.properties.example application.properties
   
   # Edit application.properties with your database credentials
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the API**
   - API Base URL: `http://localhost:8080`
   - Default Admin: `admin@gmail.com` / `admin123`

## 🔧 Configuration

### Environment Variables
Set these environment variables for production:

| Variable | Description | Example |
|----------|-------------|---------|
| `DATABASE_URL` | MySQL connection URL | `jdbc:mysql://localhost:3306/To_Do` |
| `DATABASE_USERNAME` | Database username | `root` |
| `DATABASE_PASSWORD` | Database password | `yourpassword` |
| `JWT_SECRET` | JWT signing key | `your-256-bit-secret` |
| `PORT` | Server port (optional) | `8080` |

### Configuration Files
- `application.properties.example` - Template configuration file
- `.env` (optional) - Local development environment variables (not in git)

### Default Configuration
```properties
# Copy from application.properties.example
spring.application.name=To-Do
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/To_Do
spring.datasource.username=root
spring.datasource.password=your_password
jwt.secret_key=your_jwt_secret
```

## 📚 API Documentation

### Authentication Endpoints

#### Register New User
```http
POST /register
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "password": "securepassword123"
}
```

#### Login
```http
POST /login
Content-Type: application/json

{
  "email": "admin@gmail.com",
  "password": "admin123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "admin@gmail.com"
}
```

### Task Management Endpoints (Requires Authentication)

All task endpoints require a valid JWT token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

#### Get User's Tasks
```http
GET /users/{userId}/tasks
Authorization: Bearer <token>
```

#### Create New Task
```http
POST /users/{userId}/tasks
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "Complete project",
  "description": "Finish the Spring Boot API",
  "status": "PENDING",
  "dueDate": "2024-12-31T23:59:59"
}
```

#### Update Task
```http
PATCH /users/{userId}/tasks/{taskId}
Authorization: Bearer <token>
Content-Type: application/json

{
  "status": "COMPLETED"
}
```

#### Delete Task
```http
DELETE /users/{userId}/tasks/{taskId}
Authorization: Bearer <token>
```

### Admin Endpoints (ADMIN Role Required)

#### Get All Users
```http
GET /users
Authorization: Bearer <token>
```

#### Get All Roles
```http
GET /roles
Authorization: Bearer <token>
```

#### Get All Tasks (System-wide)
```http
GET /tasks
Authorization: Bearer <token>
```

## 👥 User Roles

### USER Role
- Register and login
- Manage own tasks (CRUD operations)
- View own profile

### ADMIN Role
- All USER permissions
- View all users in the system
- View all tasks in the system
- Manage roles

## 🗄️ Database Schema

### Tables
- **user** - User accounts and credentials
- **role** - Available roles (USER, ADMIN)
- **user_roles** - Many-to-many relationship between users and roles
- **task** - Todo tasks with status and due dates

### Migrations
Flyway manages database schema through version-controlled SQL files:

1. **V1__create_tables.sql** - Creates all database tables
2. **V2__insert_roles.sql** - Inserts default USER and ADMIN roles

## 🔒 Security Features

- **JWT Tokens** - Stateless authentication with configurable expiration
- **BCrypt Password Encoding** - Secure password hashing
- **Role-based Access Control** - Fine-grained endpoint permissions
- **CSRF Protection** - Enabled for all state-changing operations
- **SQL Injection Prevention** - Using JPA/Hibernate parameterized queries
- **CORS Configuration** - Configurable for frontend integration

## 🧪 Testing

Run the test suite:
```bash
mvn test
```


## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/example/To_Do/
│   │   ├── config/                 # Configuration classes
│   │   │   ├── SecurityConfig.java # Security configuration
│   │   │   └── HashPassword.java   # Password encoder
│   │   ├── controller/             # REST controllers
│   │   ├── data/                   # Data initializers
│   │   │   └── initializer/
│   │   │       └── AdminDataInitializer.java
│   │   ├── dto/                    # Data transfer objects
│   │   ├── entity/                 # JPA entities
│   │   ├── repo/                   # Repository interfaces
│   │   ├── security/               # Security components
│   │   │   ├── JwtFilter.java      # JWT authentication filter
│   │   │   └── JwtUtil.java        # JWT utilities
│   │   └── service/                # Business logic
│   └── resources/
│       ├── db/migration/           # Flyway SQL migrations
│       │   ├── V1__create_tables.sql
│       │   └── V2__insert_roles.sql
│       └── application.properties  # Main configuration
└── test/                           # Test classes
```

## 📊 Project Status

✅ **Complete and Production Ready**
- [x] Core API functionality
- [x] Authentication & Authorization
- [x] Database migrations
- [x] Security implementation
- [x] Documentation
- [ ] Frontend integration (planned)
- [ ] API documentation with Swagger (planned)
- [ ] CI/CD pipeline (planned)

---

## 🌟 Features in Detail

### 1. **JWT Authentication**
- Token-based stateless authentication
- Configurable expiration time
- Secure token validation
- Automatic token refresh handling

### 2. **Database Management**
- Flyway for version-controlled migrations
- Automatic schema creation
- Default data population
- Rollback support

### 3. **Security**
- Password encryption with BCrypt
- Role-based endpoint protection
- CORS configuration
- Input validation

### 4. **Error Handling**
- Custom exception handling
- Meaningful error messages
- Proper HTTP status codes
- Logging for debugging

### 5. **Code Quality**
- Clean architecture
- Separation of concerns
- Comprehensive comments
- Follows Spring Boot best practices

---

**Built with ❤️ using Spring Boot**

