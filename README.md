# Student Management System

A Java-based Student Management System that provides CRUD operations for managing student records. The system uses file-based storage to persist data between sessions.

## Features

- Add new students with roll number, name, grade, and contact information
- View all registered students
- Update existing student information
- Delete student records
- Search students by roll number
- Search students by name
- Automatic data persistence using file storage
- Command-line interface for easy interaction

## Project Structure
'''bash
C:\Users\yugas\Downloads\project1\
├── src\
│   ├── com\
│   │   └── example\
│   │       ├── Main.java
│   │       ├── model\
│   │       │   └── Student.java
│   │       └── service\
│   │           └── StudentService.java
└── out\ 

## Requirements

- Java Development Kit (JDK) 8 or higher
- Command-line interface for running the application

## How to Run

1. Open a terminal or command prompt and navigate to the project root directory.

2. Compile the Java files:

   ```bash
   javac src/com/example/model/Student.java src/com/example/service/StudentService.java src/com/example/Main.java

3. Run the application:
   ```bash
   java -cp src com.example.Main
