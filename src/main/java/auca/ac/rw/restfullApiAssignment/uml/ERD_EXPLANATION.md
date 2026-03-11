# Entity Relationship Diagram (ERD) - Explanation

## Overview
This ERD represents a **Course Registration System** with **8 entities** (tables) and demonstrates various types of relationships including One-to-One, One-to-Many, Many-to-Many, and Self-Referencing relationships.

---

## Entities (Tables)

### 1. **students**
- **Primary Key**: student_id (VARCHAR 20)
- **Purpose**: Stores student information
- **Key Fields**:
  - name, email (unique), phone, address
  - location_id (FK to locations)
  - max_credits, min_credits (credit limits)
  - created_at (timestamp)

### 2. **instructors**
- **Primary Key**: instructor_id (VARCHAR 20)
- **Purpose**: Stores instructor/faculty information
- **Key Fields**:
  - name, email (unique), phone
  - department_id (FK to departments)
  - specialization, office_hours
  - created_at (timestamp)

### 3. **courses**
- **Primary Key**: course_code (VARCHAR 20)
- **Purpose**: Stores course offerings
- **Key Fields**:
  - title, credits, capacity, enrolled_count
  - instructor_id (FK to instructors)
  - department_id (FK to departments)
  - schedule_time, schedule_days, location
  - semester_id, description, fee
  - is_active (boolean), created_at

### 4. **departments**
- **Primary Key**: dept_id (VARCHAR 20)
- **Purpose**: Stores academic departments
- **Key Fields**:
  - dept_name (department name)
  - office_location

### 5. **locations**
- **Primary Key**: id (UUID)
- **Purpose**: Stores hierarchical location data (Country -> Province -> District -> Sector -> Cell -> Village)
- **Key Fields**:
  - code (unique), name
  - type (ENUM: COUNTRY, PROVINCE, DISTRICT, SECTOR, CELL, VILLAGE)
  - parent_id (FK to locations - self-referencing)

### 6. **users**
- **Primary Key**: user_id (VARCHAR 20)
- **Purpose**: Authentication and authorization
- **Key Fields**:
  - username (unique), password, email (unique)
  - role (ENUM: STUDENT, INSTRUCTOR, ADMIN)
  - student_id (FK to students)
  - instructor_id (FK to instructors)
  - is_active, last_login, created_at

### 7. **registrations**
- **Primary Key**: registration_id (INTEGER AUTO_INCREMENT)
- **Purpose**: Tracks student course enrollments
- **Key Fields**:
  - student_id (FK to students)
  - course_code (FK to courses)
  - registration_date, status (ENUM)
  - grade, approval_required
  - approved_by, approval_date, created_at

### 8. **payments**
- **Primary Key**: payment_id (INTEGER AUTO_INCREMENT)
- **Purpose**: Tracks student payments
- **Key Fields**:
  - student_id (FK to students)
  - amount, payment_type (ENUM)
  - payment_method (ENUM), transaction_id
  - payment_date, status (ENUM)
  - receipt_number, semester_id

### 9. **waitlist**
- **Primary Key**: waitlist_id (INTEGER AUTO_INCREMENT)
- **Purpose**: Manages course waitlists when capacity is full
- **Key Fields**:
  - student_id (FK to students)
  - course_code (FK to courses)
  - position (queue position)
  - waitlist_date, notified

### 10. **course_prerequisites** (Join Table)
- **Composite Primary Key**: (course_code, prerequisite_code)
- **Purpose**: Many-to-Many relationship for course prerequisites
- **Key Fields**:
  - course_code (FK to courses)
  - prerequisite_code (FK to courses)

---

## Relationships Explained

### 1. **One-to-One Relationships**

#### User ↔ Student
- **Mapping**: `@OneToOne` in User entity, `mappedBy = "student"` in Student
- **Logic**: Each user account can be linked to ONE student record
- **Foreign Key**: user.student_id → students.student_id
- **Use Case**: Authentication for student portal access

#### User ↔ Instructor
- **Mapping**: `@OneToOne` in User entity, `mappedBy = "instructor"` in Instructor
- **Logic**: Each user account can be linked to ONE instructor record
- **Foreign Key**: user.instructor_id → instructors.instructor_id
- **Use Case**: Authentication for instructor portal access
- **Note**: A user is EITHER a student OR instructor, not both (determined by role field)

---

### 2. **One-to-Many Relationships**

#### Location → Students
- **Mapping**: `@ManyToOne` in Student, `@OneToMany(mappedBy = "location")` in Location
- **Logic**: One location can have MANY students, but each student belongs to ONE location
- **Foreign Key**: students.location_id → locations.id
- **Use Case**: Track student geographical distribution

#### Department → Instructors
- **Mapping**: `@ManyToOne` in Instructor, `@OneToMany(mappedBy = "department")` in Department
- **Logic**: One department has MANY instructors, each instructor belongs to ONE department
- **Foreign Key**: instructors.department_id → departments.dept_id
- **Use Case**: Organize faculty by academic department

#### Department → Courses
- **Mapping**: `@ManyToOne` in Course, `@OneToMany(mappedBy = "department")` in Department
- **Logic**: One department offers MANY courses, each course belongs to ONE department
- **Foreign Key**: courses.department_id → departments.dept_id
- **Use Case**: Course catalog organization

#### Instructor → Courses
- **Mapping**: `@ManyToOne` in Course, `@OneToMany(mappedBy = "instructor")` in Instructor
- **Logic**: One instructor teaches MANY courses, each course has ONE instructor
- **Foreign Key**: courses.instructor_id → instructors.instructor_id
- **Use Case**: Instructor workload and schedule management

#### Student → Registrations
- **Mapping**: `@ManyToOne` in Registration, `@OneToMany(mappedBy = "student")` in Student
- **Logic**: One student can have MANY registrations (multiple courses), each registration belongs to ONE student
- **Foreign Key**: registrations.student_id → students.student_id
- **Use Case**: Track all courses a student is enrolled in

#### Course → Registrations
- **Mapping**: `@ManyToOne` in Registration, `@OneToMany(mappedBy = "course")` in Course
- **Logic**: One course can have MANY registrations (multiple students), each registration is for ONE course
- **Foreign Key**: registrations.course_code → courses.course_code
- **Use Case**: Track all students enrolled in a course

#### Student → Payments
- **Mapping**: `@ManyToOne` in Payment, `@OneToMany(mappedBy = "student")` in Student
- **Logic**: One student can make MANY payments, each payment belongs to ONE student
- **Foreign Key**: payments.student_id → students.student_id
- **Use Case**: Financial transaction history

#### Student → Waitlist
- **Mapping**: `@ManyToOne` in Waitlist, `@OneToMany(mappedBy = "student")` in Student
- **Logic**: One student can be on MANY waitlists, each waitlist entry belongs to ONE student
- **Foreign Key**: waitlist.student_id → students.student_id
- **Use Case**: Track which courses a student is waiting for

#### Course → Waitlist
- **Mapping**: `@ManyToOne` in Waitlist, `@OneToMany(mappedBy = "course")` in Course
- **Logic**: One course can have MANY waitlist entries, each entry is for ONE course
- **Foreign Key**: waitlist.course_code → courses.course_code
- **Use Case**: Manage course waitlist queue

---

### 3. **Many-to-Many Relationship**

#### Course ↔ Course (Prerequisites)
- **Mapping**: `@ManyToMany` with `@JoinTable` in Course entity
- **Join Table**: course_prerequisites
- **Logic**: 
  - A course can have MANY prerequisites
  - A course can be a prerequisite for MANY other courses
- **Foreign Keys**:
  - course_prerequisites.course_code → courses.course_code
  - course_prerequisites.prerequisite_code → courses.course_code
- **Example**:
  - "Advanced Java" requires "Intro to Java" and "Data Structures"
  - "Intro to Java" is prerequisite for "Advanced Java", "Web Development", "Mobile Apps"
- **Use Case**: Enforce course prerequisites during registration

---

### 4. **Self-Referencing Relationship**

#### Location → Location (Hierarchical)
- **Mapping**: `@ManyToOne` for parent, `@OneToMany(mappedBy = "parent")` for children
- **Logic**: Locations form a tree structure (hierarchy)
- **Foreign Key**: locations.parent_id → locations.id
- **Hierarchy Levels**:
  1. COUNTRY (Rwanda)
  2. PROVINCE (Eastern, Western, etc.)
  3. DISTRICT (Kigali, Musanze, etc.)
  4. SECTOR
  5. CELL
  6. VILLAGE
- **Example**:
  ```
  Rwanda (COUNTRY)
    └── Eastern Province (PROVINCE)
          └── Rwamagana District (DISTRICT)
                └── Muhazi Sector (SECTOR)
                      └── Gahini Cell (CELL)
                            └── Kajevuba Village (VILLAGE)
  ```
- **Use Case**: Detailed geographical tracking and reporting

---

## Cardinality Notation

- `||--||` : One-to-One (exactly one)
- `||--o{` : One-to-Many (one to zero or more)
- `}o--o{` : Many-to-Many (zero or more to zero or more)

---

## Key Design Patterns

### 1. **Bridge/Junction Table Pattern**
- **registrations**: Connects students and courses with additional metadata (status, grade)
- **course_prerequisites**: Pure join table for many-to-many relationship

### 2. **Hierarchical Data Pattern**
- **locations**: Self-referencing for tree structure

### 3. **Polymorphic Association Pattern**
- **users**: Can link to either student OR instructor (role-based)

### 4. **Audit Trail Pattern**
- Most tables include `created_at` timestamp for tracking

---

## Business Rules Enforced

1. **Email Uniqueness**: Students, instructors, and users must have unique emails
2. **Credit Limits**: Students have min/max credit constraints (12-18)
3. **Course Capacity**: Courses have enrollment limits (capacity vs enrolled_count)
4. **Waitlist Management**: When course is full, students join waitlist with position tracking
5. **Prerequisites**: Students must complete prerequisite courses before enrolling
6. **Payment Tracking**: All student payments are recorded with status and method
7. **Registration Approval**: Some registrations require approval workflow
8. **Location Hierarchy**: Enforces proper geographical structure

---

## How to View the ERD

1. Install PlantUML plugin in your IDE (IntelliJ, VS Code, Eclipse)
2. Open `ERD.puml` file
3. Right-click and select "Preview PlantUML" or use the preview pane
4. The diagram will render showing all entities and relationships

Alternatively, use online PlantUML viewer:
- https://www.plantuml.com/plantuml/uml/
- Copy the content of ERD.puml and paste it

---

## Summary

- **Total Tables**: 8 main entities + 1 join table = 9 tables
- **One-to-One**: 2 relationships (User-Student, User-Instructor)
- **One-to-Many**: 9 relationships
- **Many-to-Many**: 1 relationship (Course Prerequisites)
- **Self-Referencing**: 1 relationship (Location hierarchy)
- **Total Relationships**: 13 relationships

This ERD provides a comprehensive view of the Course Registration System's data model, supporting complex academic operations including enrollment, prerequisites, payments, and geographical tracking.
