package com.example;

import com.example.model.Student;
import com.example.service.StudentService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static StudentService studentService = new StudentService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // studentService.loadStudentsFromFile(); // Already called in constructor

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Enter your choice: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        viewAllStudents();
                        break;
                    case 3:
                        updateStudent();
                        break;
                    case 4:
                        deleteStudent();
                        break;
                    case 5:
                        searchStudentByRollNumber();
                        break;
                    case 6:
                        searchStudentsByName();
                        break;
                    case 7:
                        studentService.saveStudentsToFile(); // Explicit save option
                        break;
                    case 0:
                        running = false;
                        System.out.println("Exiting application. Saving data...");
                        studentService.saveStudentsToFile(); // Save on exit
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
            }
            System.out.println("------------------------------------");
        }
        scanner.close();
        System.out.println("Application closed.");
    }

    private static void printMenu() {
        System.out.println("\n--- Student Management System ---");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Search Student by Roll Number");
        System.out.println("6. Search Students by Name");
        System.out.println("7. Save Data to File Manually");
        System.out.println("0. Exit");
        System.out.println("------------------------------------");
    }

    private static void addStudent() {
        System.out.println("\n--- Add New Student ---");
        int rollNumber = 0;
        boolean validRoll = false;
        while(!validRoll) {
            try {
                System.out.print("Enter Roll Number: ");
                rollNumber = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (rollNumber <= 0) {
                    System.out.println("Roll number must be a positive integer.");
                } else {
                    validRoll = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for Roll Number. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        while (name.trim().isEmpty()) {
            System.out.print("Name cannot be empty. Enter Name: ");
            name = scanner.nextLine();
        }

        System.out.print("Enter Grade (e.g., 10th, B.Tech): ");
        String grade = scanner.nextLine();
         while (grade.trim().isEmpty()) {
            System.out.print("Grade cannot be empty. Enter Grade: ");
            grade = scanner.nextLine();
        }

        System.out.print("Enter Contact Number (optional, press Enter to skip): ");
        String contact = scanner.nextLine();

        Student newStudent = new Student(rollNumber, name, grade, contact.isEmpty() ? "N/A" : contact);
        studentService.addStudent(newStudent);
    }

    private static void viewAllStudents() {
        System.out.println("\n--- All Students ---");
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found in the system.");
        } else {
            students.forEach(System.out::println);
        }
    }

    private static void updateStudent() {
        System.out.println("\n--- Update Student ---");
        System.out.print("Enter Roll Number of student to update: ");
        int rollNumber;
        try {
            rollNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            System.out.println("Invalid Roll Number format.");
            scanner.nextLine(); // consume
            return;
        }

        Optional<Student> studentOpt = studentService.getStudentByRollNumber(rollNumber);
        if (studentOpt.isPresent()) {
            Student currentStudent = studentOpt.get();
            System.out.println("Current details: " + currentStudent);

            System.out.print("Enter new Name (or press Enter to keep current '" + currentStudent.getName() + "'): ");
            String newName = scanner.nextLine();
            if (newName.trim().isEmpty()) newName = currentStudent.getName();

            System.out.print("Enter new Grade (or press Enter to keep current '" + currentStudent.getGrade() + "'): ");
            String newGrade = scanner.nextLine();
            if (newGrade.trim().isEmpty()) newGrade = currentStudent.getGrade();

            System.out.print("Enter new Contact (or press Enter to keep current '" + currentStudent.getContactNumber() + "'): ");
            String newContact = scanner.nextLine();
            if (newContact.trim().isEmpty()) newContact = currentStudent.getContactNumber();


            studentService.updateStudent(rollNumber, newName, newGrade, newContact);
        } else {
            System.out.println("Student with Roll Number " + rollNumber + " not found.");
        }
    }

    private static void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        System.out.print("Enter Roll Number of student to delete: ");
        int rollNumber;
         try {
            rollNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            System.out.println("Invalid Roll Number format.");
            scanner.nextLine(); // consume
            return;
        }
        studentService.deleteStudent(rollNumber);
    }

    private static void searchStudentByRollNumber() {
        System.out.println("\n--- Search Student by Roll Number ---");
        System.out.print("Enter Roll Number to search: ");
        int rollNumber;
        try {
            rollNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            System.out.println("Invalid Roll Number format.");
            scanner.nextLine(); // consume
            return;
        }

        Optional<Student> studentOpt = studentService.getStudentByRollNumber(rollNumber);
        if (studentOpt.isPresent()) {
            System.out.println("Student Found: " + studentOpt.get());
        } else {
            System.out.println("Student with Roll Number " + rollNumber + " not found.");
        }
    }

    private static void searchStudentsByName() {
        System.out.println("\n--- Search Students by Name ---");
        System.out.print("Enter Name (or part of name) to search: ");
        String nameQuery = scanner.nextLine();

        List<Student> foundStudents = studentService.searchStudentsByName(nameQuery);
        if (foundStudents.isEmpty()) {
            System.out.println("No students found matching '" + nameQuery + "'.");
        } else {
            System.out.println("Students found matching '" + nameQuery + "':");
            foundStudents.forEach(System.out::println);
        }
    }
}