# Sorting and Pagination Implementation

## Overview
This document explains how sorting and pagination are implemented in the Student, Course, and Location controllers using Spring Data JPA.

---

## Implementation Details

### 1. **Repositories**
All repositories extend `JpaRepository`, which provides built-in support for pagination and sorting:
- `StudentRepository extends JpaRepository<StudentEntity, String>`
- `CourseRepository extends JpaRepository<CourseEntity, String>`
- `LocationRepository extends JpaRepository<Location, UUID>`

### 2. **Service Layer**
Each service has two methods:
- **Without Pagination**: `findAll()` - Returns `List<Entity>`
- **With Pagination**: `findAll(Pageable pageable)` - Returns `Page<Entity>`

Example from StudentService:
```java
public List<StudentEntity> getAllStudents() {
    return studentRepository.findAll();
}

public Page<StudentEntity> getAllStudents(Pageable pageable) {
    return studentRepository.findAll(pageable);
}
```

### 3. **Controller Layer**
Each controller has two endpoints:

#### Original Endpoint (No Pagination)
- `/api/students/all`
- `/api/courses/all`
- `/api/locations`

#### New Paginated Endpoint
- `/api/students/paginated`
- `/api/courses/paginated`
- `/api/locations/paginated`

---

## How Sorting Works

Sorting is implemented using Spring Data JPA's `Sort` class:

```java
Sort.Direction sortDirection = Sort.Direction.fromString(direction);
Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
```

**Parameters:**
- `sortBy`: Field name to sort by (e.g., "studentId", "name", "email")
- `direction`: Sort direction - "ASC" (ascending) or "DESC" (descending)

**SQL Translation:**
Spring Data JPA converts this to SQL `ORDER BY` clause:
```sql
SELECT * FROM student ORDER BY student_id ASC LIMIT 10 OFFSET 0
```

---

## How Pagination Works

Pagination divides large datasets into smaller pages using the `Pageable` interface:

```java
Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
Page<Entity> result = repository.findAll(pageable);
```

**Parameters:**
- `page`: Page number (0-indexed, default: 0)
- `size`: Number of records per page (default: 10)

**SQL Translation:**
```sql
SELECT * FROM student LIMIT 10 OFFSET 0
```
- `LIMIT`: Number of records to fetch (size)
- `OFFSET`: Number of records to skip (page * size)

---

## API Usage Examples

### 1. StudentController

#### Get all students (paginated, sorted)
```
GET /api/students/paginated?page=0&size=10&sortBy=name&direction=ASC
```

**Parameters:**
- `page`: Page number (default: 0)
- `size`: Records per page (default: 10)
- `sortBy`: Field to sort by (default: "studentId")
- `direction`: ASC or DESC (default: "ASC")

**Response:**
```json
{
  "content": [
    {
      "studentId": "S001",
      "name": "John Doe",
      "email": "john@example.com"
    }
  ],
  "totalElements": 150,
  "totalPages": 15,
  "size": 10,
  "number": 0,
  "sort": {
    "sorted": true,
    "unsorted": false
  },
  "first": true,
  "last": false
}
```

#### Examples:
```
# First page, 10 students, sorted by name ascending
GET /api/students/paginated?page=0&size=10&sortBy=name&direction=ASC

# Second page, 20 students, sorted by email descending
GET /api/students/paginated?page=1&size=20&sortBy=email&direction=DESC

# Third page, default size (10), sorted by studentId
GET /api/students/paginated?page=2
```

---

### 2. CourseController

#### Get all courses (paginated, sorted)
```
GET /api/courses/paginated?page=0&size=10&sortBy=title&direction=ASC
```

**Sortable Fields:**
- `courseCode`
- `title`
- `credits`
- `department`

#### Examples:
```
# First page, 15 courses, sorted by title
GET /api/courses/paginated?page=0&size=15&sortBy=title&direction=ASC

# All courses sorted by credits descending
GET /api/courses/paginated?sortBy=credits&direction=DESC
```

---

### 3. LocationController

#### Get all locations (paginated, sorted)
```
GET /api/locations/paginated?page=0&size=10&sortBy=name&direction=ASC
```

**Sortable Fields:**
- `code`
- `name`
- `type`

#### Examples:
```
# First page, 20 locations, sorted by code
GET /api/locations/paginated?page=0&size=20&sortBy=code&direction=ASC

# Second page, sorted by name descending
GET /api/locations/paginated?page=1&sortBy=name&direction=DESC
```

---

## Performance Benefits

### 1. **Reduced Memory Usage**
- Loads only required records instead of entire dataset
- Example: Loading 10 records instead of 10,000

### 2. **Faster Query Execution**
- Database fetches limited rows using `LIMIT` clause
- Reduces database processing time

### 3. **Better Network Performance**
- Smaller response payload
- Faster data transfer between server and client

### 4. **Improved User Experience**
- Quick initial page load
- Lazy loading for additional data
- Smooth navigation between pages

### 5. **Scalability**
- Application can handle large datasets efficiently
- Consistent performance regardless of data size

---

## Page Object Properties

The `Page<T>` object returned contains:

| Property | Description |
|----------|-------------|
| `content` | List of entities for current page |
| `totalElements` | Total number of records in database |
| `totalPages` | Total number of pages |
| `size` | Number of records per page |
| `number` | Current page number (0-indexed) |
| `first` | Boolean - is this the first page? |
| `last` | Boolean - is this the last page? |
| `sort` | Sort information |
| `numberOfElements` | Number of elements in current page |

---

## Testing the Implementation

### Using cURL:

```bash
# Test Student pagination
curl -X GET "http://localhost:8080/api/students/paginated?page=0&size=5&sortBy=name&direction=ASC"

# Test Course pagination
curl -X GET "http://localhost:8080/api/courses/paginated?page=0&size=10&sortBy=title&direction=DESC"

# Test Location pagination
curl -X GET "http://localhost:8080/api/locations/paginated?page=0&size=15&sortBy=code&direction=ASC"
```

### Using Postman:
1. Create GET request to paginated endpoint
2. Add query parameters: page, size, sortBy, direction
3. Send request and observe paginated response

---

## Key Points for Viva-Voce

1. **Pageable Interface**: Spring Data JPA interface that encapsulates pagination and sorting information
2. **PageRequest.of()**: Factory method to create Pageable instances
3. **Sort.by()**: Creates sort specification for one or more fields
4. **Page<T>**: Return type containing paginated data and metadata
5. **SQL Translation**: Spring automatically converts Pageable to SQL LIMIT/OFFSET
6. **Performance**: Pagination reduces memory usage and improves query performance
7. **Zero-indexed**: Page numbers start from 0, not 1
8. **Default Values**: Use @RequestParam with defaultValue for optional parameters
