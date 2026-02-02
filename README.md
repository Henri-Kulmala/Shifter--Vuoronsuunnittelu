# Shifter – Shift Planning Backend

<br/>
Frontend-application can be found from:
<br/>
<br/>

<a href="https://github.com/Henri-Kulmala/Shifter-App-React-Client">
<img alt="Static Badge" src="https://img.shields.io/badge/-Shifter%20React%20Application-green?style=flat" >
</a>

<br/>

<br/>
Shifter is a Java Spring Boot backend application built to support workforce shift planning and employee management in a retail environment.  
The application was developed as part of a university course and designed around a real workplace use case.

The backend is responsible for authentication, authorization, business logic, data persistence, and RESTful APIs consumed by a separate frontend client. 

---

## Purpose & Use Case

The system is designed for store managers to:
- Plan daily work shifts
- Assign employees to shifts and workstations
- Define working hours and breaks
- Manage users and employee data
- Persist workday data for scheduling and reporting
- Support exporting shift data for sharing (PDF handled by frontend)

The application models an internal management tool rather than a public-facing service.

---

## Tech Stack

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Maven
- MySQL
- Docker
- JUnit / SpringBootTest

---

## Architecture Overview

The backend follows a layered architecture:

- **Controllers**  
  Handle RESTful HTTP requests and responses

- **Services**  
  Contain business logic and validation

- **Repositories**  
  Data access layer using Spring Data JPA

- **DTOs & Mapping**  
  Used to separate persistence models from API-facing data structures

- **Exception Handling**  
  Centralized error handling for runtime and validation errors

---

## Security & Authentication

Authentication is implemented using Spring Security with session-based login.

Key characteristics:
- Form-based authentication
- Session handling via `JSESSIONID`
- BCrypt password hashing
- Role-based authorization
- CSRF protection enabled

Authorization rules:
- Public access: `/login`, `/resources/**`
- Restricted access: `/api/**`, `/shiftplanner/**` (ADMIN only)
- All other requests require authentication

---

## Database Schema

The application uses a relational MySQL database.  
The schema is designed to model employees, users, workdays, shifts, and break coverage.

### Core Tables

#### Employee
Stores employee information used for shift assignment.
```sql
CREATE TABLE employee (
  employee_id BIGINT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  qualification TINYINT(1),
  notes VARCHAR(255),
  PRIMARY KEY (employee_id)
);
````
#### User
````sql
CREATE TABLE user (
  user_id BIGINT NOT NULL AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  role VARCHAR(255),
  employee_id BIGINT,
  PRIMARY KEY (user_id),
  UNIQUE (username),
  FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
);
````

#### Workday
````sql
CREATE TABLE workday (
  id BIGINT NOT NULL AUTO_INCREMENT,
  date DATE NOT NULL,
  PRIMARY KEY (id)
);

````

#### Shift
````sql
CREATE TABLE shift (
  shiftid BIGINT NOT NULL AUTO_INCREMENT,
  start_time TIME(6) NOT NULL,
  end_time TIME(6) NOT NULL,
  shift_name VARCHAR(255),
  workstation VARCHAR(255),
  employee_id BIGINT,
  workday_id BIGINT,
  covering_shift_id BIGINT,
  cover_employee_id BIGINT,
  PRIMARY KEY (shiftid),
  FOREIGN KEY (employee_id) REFERENCES employee(employee_id),
  FOREIGN KEY (workday_id) REFERENCES workday(id),
  FOREIGN KEY (covering_shift_id) REFERENCES shift(shiftid),
  FOREIGN KEY (cover_employee_id) REFERENCES employee(employee_id)
);

````
## DTO-Oriented shifts
Some tables exist primarily to support DTO mapping and data transfer.

#### Shifts (DTO)
````sql
CREATE TABLE shifts (
  shift_id BIGINT NOT NULL AUTO_INCREMENT,
  workstation VARCHAR(255),
  shift_name VARCHAR(255),
  start_time DATETIME,
  end_time DATETIME,
  employee_id BIGINT,
  covering_shift_id BIGINT,
  PRIMARY KEY (shift_id),
  FOREIGN KEY (employee_id) REFERENCES employee(employee_id) ON DELETE SET NULL,
  FOREIGN KEY (covering_shift_id) REFERENCES shifts(shift_id)
);


````

#### Shift Breaks (DTO)
````sql
CREATE TABLE shift_breaks (
  shift_shiftid BIGINT NOT NULL,
  break_start TIME(6) NOT NULL,
  break_end TIME(6) NOT NULL,
  break_type VARCHAR(255),
  cover_employee_employee_id BIGINT,
  FOREIGN KEY (shift_shiftid) REFERENCES shift(shiftid),
  FOREIGN KEY (cover_employee_employee_id) REFERENCES employee(employee_id)
);


````
---

## Testing 
The project includes backend testing using:

- @SpringBootTest
- Integration-level tests
- Validation of service and repository behavior

---

## Build & Run (Local)
#### Requirements
- Java 17+ (or compatible version)
- Maven
- MySQL
- Docker (optional)

#### Build 
```` bash
mvn clean package
````


#### Run 
```` bash
mvn spring-boot:run
````


#### Docker (optional) 
```` bash
docker build -t shifter-backend .
docker run -p 8080:8080 shifter-backend
````
---

## Environment Configuration
The application expects standard Spring environment variables, such as:

- Database URL
- Database username
- Database password

These are typically provided via application.properties or environment variables.

---

## Project Status 
This project was developed for educational and portfolio purposes.
While based on a real workplace use case, it is not actively maintained for production deployment.



