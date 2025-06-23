# Course Management API

A Spring Boot RESTful API for managing courses and students, supporting many-to-many relationships, DTO-based data transfer, and robust error handling.

## Features

- CRUD operations for Courses and Students
- Assign students to courses and vice versa
- DTOs for clean API contracts
- Global exception handling for clear error responses
- Validation for input data

## Technologies Used

- Java 21
- Spring Boot 3
- Spring Data JPA
- Lombok
- Jackson
- H2/MySQL/PostgreSQL (configurable)

## API Endpoints

### Courses

- `POST   /api/courses` — Create a course
- `GET    /api/courses` — List all courses
- `GET    /api/courses/{id}` — Get course by ID
- `PUT    /api/courses/{id}` — Update course
- `DELETE /api/courses/{id}` — Delete course
- `GET    /api/courses/{courseId}/students` — List students in a course

#### Example Course JSON

```json
{
  "name": "Math 101",
  "description": "Basic Mathematics",
  "studentIds": [1, 2]
}
```

### Students

- `POST   /api/students` — Create a student
- `GET    /api/students` — List all students
- `GET    /api/students/{id}` — Get student by ID
- `PUT    /api/students/{id}` — Update student
- `DELETE /api/students/{id}` — Delete student
- `POST   /api/students/{studentId}/courses/{courseId}` — Assign course to student
- `GET    /api/students/{studentId}/courses` — List courses for a student

#### Example Student JSON

```json
{
  "firstName": "Alice",
  "lastName": "Smith",
  "email": "alice@example.com",
  "courseIds": [1]
}
```

## Error Handling

- Returns clear JSON error responses for not found, validation, and server errors.

## Getting Started

1. Clone the repo
2. Configure your database in `src/main/resources/application.properties`
3. Build and run with Maven:
   ```sh
   ./mvnw spring-boot:run
   ```
4. Test endpoints with Postman, curl, or your favorite tool.
