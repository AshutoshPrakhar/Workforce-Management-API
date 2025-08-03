# Workforce Management API

A backend service built with **Spring Boot 3.4.8** and **Java 17** for managing staff tasks in a workforce environment. The API supports creating, assigning, and tracking tasks, along with resolving bugs and implementing enhancements as per the provided specification.

---

## 📌 Features Implemented

### ✅ Bug Fixes
1. **Fix 1 – Staff ID Null Issue**: Corrected the logic where newly created staff entries were returned without IDs.
2. **Fix 2 – Task Not Marking Completed**: Fixed bug where tasks remained in `PENDING` state despite meeting completion criteria.
3. **Fix 3 – Task Duplication on Reassignment**: Prevented duplicate tasks when reassigned.

### ✅ New Features
1. **Create Task with Priority**: Tasks can now be created with `LOW`, `MEDIUM`, or `HIGH` priority.
2. **Get All Tasks by Date Range**: Retrieve all tasks assigned between a start and end date.
3. **Find Most Assigned Staff**: Get the staff member who has been assigned the highest number of tasks.

---

## 🛠️ Tech Stack

- **Language**: Java 17
- **Framework**: Spring Boot 3.4.8
- **Build Tool**: Gradle
- **Mapping**: MapStruct
- **Testing**: JUnit 5
- **Data Layer**: In-memory storage (no DB)

---

## 📂 Project Structure

src
└── main
├── java
│ └── com.company.workforcemgmt
│ ├── controller
│ ├── dto
│ ├── model
│ ├── service
│ └── repository (in-memory)
└── resources
