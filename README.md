# Online Course Registration System - RESTful API


A comprehensive Spring Boot RESTful API for managing a course registration system with students, courses, instructors, departments, payments, and registrations.

## Table of Contents
- [Project Overview](#project-overview)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Database Setup](#database-setup)
- [Installation & Setup](#installation--setup)
- [API Endpoints](#api-endpoints)
- [Implementation Steps](#implementation-steps)
- [Testing the API](#testing-the-api)

---

## Project Overview

This project is a microservice application built with Spring Boot that provides RESTful APIs for a course registration system. It includes:
- Student management with location-based queries
- Course management with prerequisites
- Registration and waitlist functionality
- Payment processing
- Department and instructor management
- Pagination and sorting capabilities

---

## Technologies Used

- **Java 21**
- **Spring Boot 4.0.2**
- **Spring Data JPA** - Database operations
- **Spring Web** - RESTful API development
- **MySQL 8.0+** - Database
- **Maven** - Dependency management
- **Hibernate** - ORM framework

---

## Project Structure

```
restfullApiAssignment2/
├── src/
│   ├── main/
│   │   ├── java/auca/ac/rw/restfullApiAssignment/
│   │   │   ├── controller/          # REST Controllers
│   │   │   │   ├── StudentController.java
│   │   │   │   ├── CourseController.java
│   │   │   │   └── ...
│   │   │   ├── service/             # Business Logic
│   │   │   │   ├── StudentService.java
│   │   │   │   ├── CourseService.java
│   │   │   │   └── ...
│   │   │   ├── repository/          # Data Access Layer
│   │   │   │   ├── StudentRepository.java
│   │   │   │   ├── CourseRepository.java
│   │   │   │   └── ...
│   │   │   ├── modal/               # Entity Models
│   │   │   │   ├── StudentEntity.java
│   │   │   │   ├── CourseEntity.java
│   │   │   │   └── ...
│   │   │   └── RestfullApiAssignmentApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

---

## Database Setup

### Step 1: Create MySQL Database

```sql
CREATE DATABASE course_registration_system_db;
USE course_registration_system_db;
```

### Step 2: Configure Database Connection

Update `src/main/resources/application.properties`:

```properties
spring.application.name=restfullApiAssignment
server.port=8081

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/course_registration_system_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## Installation & Setup

### Prerequisites
- Java 21 or higher
- MySQL 8.0 or higher
- Maven 3.6+

### Step 1: Clone the Repository
```bash
cd c:\Users\YSS\OneDrive\Desktop\MidTerm\restfullApiAssignment2
```

### Step 2: Install Dependencies
```bash
mvnw clean install
```

### Step 3: Run the Application
```bash
mvnw spring-boot:run
```

The application will start on `http://localhost:8081`

---

## API Endpoints

### Student Management

#### 1. Create Student
- **POST** `/api/students/save`
- **Body:**
```json
{
  "studentId": "STU001",
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "0788123456",
  "address": "Kigali, Rwanda",
  "location": {
    "code": "LOC001"
  }
}
```

#### 2. Get All Students
- **GET** `/api/students/all`

#### 3. Get Students with Pagination & Sorting
- **GET** `/api/students/paginated?page=0&size=10&sortBy=name&direction=ASC`
- **Query Parameters:**
  - `page` - Page number (default: 0)
  - `size` - Items per page (default: 10)
  - `sortBy` - Field to sort by (default: studentId)
  - `direction` - ASC or DESC (default: ASC)

#### 4. Get Student by ID
- **GET** `/api/students/{id}`

#### 5. Update Student
- **PUT** `/api/students/{id}`
- **Body:** Same as Create Student

#### 6. Delete Student
- **DELETE** `/api/students/{id}`

#### 7. Get Students by Province Code
- **GET** `/api/students/province/code/{code}`

#### 8. Get Students by Province Name
- **GET** `/api/students/province/name/{name}`

#### 9. Get Students by Location Code
- **GET** `/api/students/location/code/{code}`

### Course Management

#### 1. Create Course
- **POST** `/api/courses/save`
- **Body:**
```json
{
  "courseCode": "CS101",
  "title": "Introduction to Programming",
  "credits": 3,
  "capacity": 30,
  "scheduleTime": "09:00-11:00",
  "scheduleDays": "Mon, Wed, Fri",
  "location": "Room 101",
  "description": "Basic programming concepts",
  "fee": 500.00
}
```

#### 2. Get All Courses
- **GET** `/api/courses/all`

#### 3. Get Courses with Pagination
- **GET** `/api/courses/paginated?page=0&size=10&sortBy=title&direction=ASC`

#### 4. Get Course by Code
- **GET** `/api/courses/{code}`

#### 5. Update Course
- **PUT** `/api/courses/{code}`

#### 6. Delete Course
- **DELETE** `/api/courses/{code}`

---

## Implementation Steps

### Step 1: Project Setup
1. Created Spring Boot project with Maven
2. Added dependencies in `pom.xml`:
   - spring-boot-starter-web
   - spring-boot-starter-data-jpa
   - mysql-connector-j

### Step 2: Database Configuration
1. Created MySQL database
2. Configured `application.properties` with database credentials
3. Set Hibernate to auto-update schema

### Step 3: Entity Layer (Models)
Created entity classes with JPA annotations:
- **StudentEntity** - Student information with location relationship
- **CourseEntity** - Course details with instructor and department
- **Location** - Hierarchical location structure (Province → District → Sector → Cell → Village)
- **Registration** - Student course registrations
- **Payment** - Payment records
- **Instructor** - Course instructors
- **Department** - Academic departments
- **User** - Authentication users
- **Waitlist** - Course waitlist management

Key Features:
- Used `@Entity` for JPA mapping
- Defined relationships: `@ManyToOne`, `@OneToMany`, `@ManyToMany`
- Added `@JsonIgnore` to prevent circular references
- Implemented validation with `nullable = false`, `unique = true`

### Step 4: Repository Layer
Created repository interfaces extending `JpaRepository`:
- **StudentRepository** - Custom queries for location-based searches
- **CourseRepository** - Course data access
- Other repositories for each entity

Custom Queries Implemented:
```java
@Query("SELECT DISTINCT s FROM StudentEntity s WHERE s.location.code = :provinceCode OR ...")
List<StudentEntity> findByProvinceCode(@Param("provinceCode") String provinceCode);
```

### Step 5: Service Layer
Implemented business logic:
- **StudentService** - Student CRUD operations with validation
- **CourseService** - Course management
- Validation for duplicate emails, student IDs
- Location relationship handling

Key Logic:
- Check for existing records before save
- Handle location relationships
- Return meaningful status messages

### Step 6: Controller Layer
Created REST controllers with proper HTTP methods:
- **StudentController** - Student endpoints
- **CourseController** - Course endpoints

Features:
- Used `@RestController` and `@RequestMapping`
- Implemented proper HTTP status codes (200, 201, 404, 409)
- Added `@RequestBody` for JSON input
- Added `@PathVariable` for URL parameters
- Implemented pagination with `Pageable`

### Step 7: Pagination & Sorting
Implemented in StudentController:
```java
@GetMapping("/paginated")
public ResponseEntity<Page<StudentEntity>> getAllStudentsPaginated(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "studentId") String sortBy,
    @RequestParam(defaultValue = "ASC") String direction) {
    
    Sort.Direction sortDirection = Sort.Direction.fromString(direction);
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
    return new ResponseEntity<>(studentService.getAllStudents(pageable), HttpStatus.OK);
}
```

### Step 8: Location-Based Queries
Implemented hierarchical location searches:
- Search by province code
- Search by province name
- Search by any location code in hierarchy

Uses recursive parent relationships to traverse location tree.

### Step 9: Testing & Validation
- Tested all endpoints with Postman
- Verified CRUD operations
- Tested pagination and sorting
- Validated error handling

---

## Testing the API

### Using Postman

#### Test 1: Create a Student
```
POST http://localhost:8081/api/students/save
Content-Type: application/json

{
  "studentId": "STU001",
  "name": "Alice Smith",
  "email": "alice@example.com",
  "phone": "0788111222",
  "address": "Kigali"
}
```

#### Test 2: Get All Students with Pagination
```
GET http://localhost:8081/api/students/paginated?page=0&size=5&sortBy=name&direction=ASC
```

#### Test 3: Get Student by ID
```
GET http://localhost:8081/api/students/STU001
```

#### Test 4: Update Student
```
PUT http://localhost:8081/api/students/STU001
Content-Type: application/json

{
  "name": "Alice Johnson",
  "email": "alice.johnson@example.com",
  "phone": "0788111222"
}
```

#### Test 5: Delete Student
```
DELETE http://localhost:8081/api/students/STU001
```

### Using cURL

```bash
# Create Student
curl -X POST http://localhost:8081/api/students/save \
  -H "Content-Type: application/json" \
  -d '{"studentId":"STU002","name":"Bob Wilson","email":"bob@example.com"}'

# Get All Students
curl http://localhost:8081/api/students/all

# Get with Pagination
curl "http://localhost:8081/api/students/paginated?page=0&size=10&sortBy=name&direction=ASC"
```

---








