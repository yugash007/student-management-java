package com.example.service;

import com.example.model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentService {
    // Using HashMap for efficient lookup by roll number
    private Map<Integer, Student> students;
    private static final String FILE_NAME = "students.dat"; // Data file

    public StudentService() {
        this.students = new HashMap<>();
        loadStudentsFromFile(); // Load data on startup
    }

    // --- CRUD Operations ---

    public boolean addStudent(Student student) {
        if (students.containsKey(student.getRollNumber())) {
            System.out.println("Error: Student with Roll Number " + student.getRollNumber() + " already exists.");
            return false;
        }
        students.put(student.getRollNumber(), student);
        System.out.println("Student added successfully: " + student.getName());
        return true;
    }

    public List<Student> getAllStudents() {
        if (students.isEmpty()) {
            return new ArrayList<>(); // Return empty list if no students
        }
        return new ArrayList<>(students.values()); // Return a copy
    }

    public Optional<Student> getStudentByRollNumber(int rollNumber) {
        return Optional.ofNullable(students.get(rollNumber));
    }

    public boolean updateStudent(int rollNumber, String newName, String newGrade, String newContact) {
        Optional<Student> studentOpt = getStudentByRollNumber(rollNumber);
        if (studentOpt.isPresent()) {
            Student studentToUpdate = studentOpt.get();
            boolean updated = false;
            if (newName != null && !newName.trim().isEmpty() && !newName.equals(studentToUpdate.getName())) {
                studentToUpdate.setName(newName);
                updated = true;
            }
            if (newGrade != null && !newGrade.trim().isEmpty() && !newGrade.equals(studentToUpdate.getGrade())) {
                studentToUpdate.setGrade(newGrade);
                updated = true;
            }
            if (newContact != null && !newContact.trim().isEmpty() && !newContact.equals(studentToUpdate.getContactNumber())) {
                studentToUpdate.setContactNumber(newContact);
                updated = true;
            }
            if (updated) {
                 System.out.println("Student record updated for Roll No: " + rollNumber);
            } else {
                System.out.println("No changes detected for Roll No: " + rollNumber);
            }
            return true;
        } else {
            System.out.println("Error: Student with Roll Number " + rollNumber + " not found for update.");
            return false;
        }
    }

    public boolean deleteStudent(int rollNumber) {
        if (students.containsKey(rollNumber)) {
            students.remove(rollNumber);
            System.out.println("Student with Roll Number " + rollNumber + " deleted successfully.");
            return true;
        } else {
            System.out.println("Error: Student with Roll Number " + rollNumber + " not found for deletion.");
            return false;
        }
    }

    // --- Search Operations ---

    public List<Student> searchStudentsByName(String nameQuery) {
        if (nameQuery == null || nameQuery.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String lowerCaseQuery = nameQuery.toLowerCase();
        return students.values().stream()
                .filter(student -> student.getName().toLowerCase().contains(lowerCaseQuery))
                .collect(Collectors.toList());
    }

    // --- File I/O Operations ---

    @SuppressWarnings("unchecked") // For casting the read object
    private void loadStudentsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Object readObject = ois.readObject();
            if (readObject instanceof HashMap) {
                students = (HashMap<Integer, Student>) readObject;
                System.out.println("Student data loaded successfully from " + FILE_NAME);
            } else {
                 System.out.println("Warning: Data file " + FILE_NAME + " contains unexpected data type. Starting fresh.");
                 students = new HashMap<>(); // Initialize fresh if type is wrong
            }
        } catch (FileNotFoundException e) {
            System.out.println("Data file (" + FILE_NAME + ") not found. Starting with an empty student list. File will be created on exit.");
            students = new HashMap<>(); // Ensure students is initialized
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading students from file: " + e.getMessage());
            e.printStackTrace();
            students = new HashMap<>(); // Initialize fresh on error
        }
    }

    public void saveStudentsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
            System.out.println("Student data saved successfully to " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error saving students to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}