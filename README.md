# CompTrack - Complaint Tracking System

![Java](https://img.shields.io/badge/Java-17-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.2-green) ![MySQL](https://img.shields.io/badge/MySQL-8.0-blue) ![Swagger](https://img.shields.io/badge/Swagger-2.1.0-brightgreen)

## Overview

CompTrack is a **Complaint Tracking System** built with Spring Boot and MySQL. It allows users to create, update, view, and delete complaints. The application provides a RESTful API for managing complaints, with a user-friendly Swagger UI for testing endpoints. The project is designed to be simple, scalable, and easy to set up for developers.

## Features

- **Create Complaints**: Users can submit complaints with a title, description, status, and associated user.
- **Update Complaints**: Modify existing complaints (e.g., change status to `IN_PROGRESS` or `RESOLVED`).
- **View Complaints**: Retrieve all complaints or a specific complaint by ID.
- **Delete Complaints**: Remove complaints from the system.
- **Swagger UI**: Interactive API documentation and testing interface.
- **No Authentication**: Simplified setup without security (authentication can be added later).

## Tech Stack

- **Backend**: Spring Boot 3.1.2, Spring Data JPA
- **Database**: MySQL 8.0
- **API Documentation**: Springdoc OpenAPI (Swagger UI) 2.1.0
- **Build Tool**: Maven
- **Language**: Java 17
- **Validation**: Hibernate Validator

## Prerequisites

Before setting up the project, ensure you have the following installed:

- **Java 17** (JDK)
- **Maven** (for building the project)
- **MySQL 8.0** (or higher)
- **IntelliJ IDEA** or any IDE (optional)
- **Git** (for cloning the repository)

## Setup Instructions

Follow these steps to run the project locally:

### 1. Clone the Repository

```bash
git clone https://github.com/arc7kai/CompTrack.git
cd CompTrack
```

### 2. Configure MySQL Database

1. Create a MySQL database named `comptrack_db`:

   ```sql
   CREATE DATABASE comptrack_db;
   ```

2. Update the database configuration in `src/main/resources/application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/comptrack_db?useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=your_mysql_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   logging.level.com.comptrack=DEBUG
   springdoc.swagger-ui.path=/swagger-ui
   springdoc.api-docs.path=/v3/api-docs
   springdoc.swagger-ui.disable-swagger-default-url=true
   ```

   Replace `your_mysql_password` with your MySQL root password.

3. Initialize the database with sample data:

   ```sql
   USE comptrack_db;

   -- Create users table
   CREATE TABLE IF NOT EXISTS users (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       username VARCHAR(255) NOT NULL,
       email VARCHAR(255) NOT NULL,
       password VARCHAR(255),
       CONSTRAINT uk_username UNIQUE (username),
       CONSTRAINT uk_email UNIQUE (email)
   );

   -- Insert test users
   INSERT IGNORE INTO users (username, email, password) VALUES
   ('testuser1', 'testuser1@example.com', 'password'),
   ('testuser2', 'testuser2@example.com', 'password'),
   ('admin', 'admin@example.com', 'password');

   -- Create complaints table
   CREATE TABLE IF NOT EXISTS complaints (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       title VARCHAR(255) NOT NULL,
       description TEXT,
       status VARCHAR(50),
       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
       user_id BIGINT,
       FOREIGN KEY (user_id) REFERENCES users(id)
   );
   ```

### 3. Build and Run the Application

1. Build the project using Maven:

   ```bash
   mvn clean install
   ```

2. Run the application:

   ```bash
   mvn spring-boot:run
   ```

   Alternatively, run it via IntelliJ IDEA by executing `CompTrackApplication.java`.

3. Access the application:
    - **Swagger UI**: [http://localhost:8080/swagger-ui](http://localhost:8080/swagger-ui)
    - **API Docs**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## API Endpoints

The following REST API endpoints are available:

| Method | Endpoint                | Description                          | Request Body Example                              |
|--------|-------------------------|--------------------------------------|--------------------------------------------------|
| POST   | `/api/complaints`       | Create a new complaint               | `{"title": "Product kharab", "description": "Nahi hua", "status": "OPEN", "userId": 1}` |
| GET    | `/api/complaints`       | Retrieve all complaints              | -                                                |
| GET    | `/api/complaints/{id}`  | Retrieve a complaint by ID           | -                                                |
| PUT    | `/api/complaints/{id}`  | Update an existing complaint         | `{"title": "Updated Issue", "description": "Still not working", "status": "IN_PROGRESS", "userId": 1}` |
| DELETE | `/api/complaints/{id}`  | Delete a complaint by ID             | -                                                |

### Example: Create a Complaint

**Request**:
```bash
curl -X POST http://localhost:8080/api/complaints \
-H "Content-Type: application/json" \
-d '{"title": "Product kharab", "description": "Nahi hua", "status": "OPEN", "userId": 1}'
```

**Response**:
```json
{
  "id": 1,
  "title": "Product kharab",
  "description": "Nahi hua",
  "status": "OPEN",
  "userId": 1,
  "createdAt": "2025-06-08T12:00:00"
}
```

## Project Structure

```
CompTrack/
├── src/
│   ├── main/
│   │   ├── java/com/comptrack/
│   │   │   ├── config/           # Configuration classes (e.g., SwaggerConfig)
│   │   │   ├── controller/       # REST controllers
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   ├── model/            # Entity classes
│   │   │   ├── repository/       # JPA repositories
│   │   │   ├── service/          # Business logic
│   │   │   └── CompTrackApplication.java
│   │   └── resources/
│   │       └── application.properties
├── pom.xml
└── README.md
```

## Contributing

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Make your changes and commit (`git commit -m "Add your feature"`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a Pull Request.

Please ensure your code follows the project's coding standards and includes tests where applicable.

## License

This project is licensed under the [MIT License](LICENSE).

## Contact

For any questions or feedback, reach out to:
- GitHub: [arc7kai](https://github.com/arc7kai)
- Email: [sashipartaps.p000@gmail.com](mailto:sashipartaps.p000@gmail.com)
