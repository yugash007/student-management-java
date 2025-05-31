package com.example.model;

import java.io.Serializable; // For File I/O

public class Student implements Serializable {
    // For serialization, it's good practice to have a serialVersionUID
    private static final long serialVersionUID = 1L;

    private int rollNumber;
    private String name;
    private String grade; // e.g., "10th", "B.Sc. CS"
    private String contactNumber; // Optional, can be added

    public Student(int rollNumber, String name, String grade, String contactNumber) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.grade = grade;
        this.contactNumber = contactNumber;
    }

    // Getters
    public int getRollNumber() {
        return rollNumber;
    }

    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    // Setters (Needed for updating records)
    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    // It's good practice to allow changing roll number, but it's the primary key.
    // For simplicity in this console app, we might avoid changing roll numbers directly.
    // If you allow it, you'd need to update the key in the HashMap in StudentService.
    // public void setRollNumber(int rollNumber) {
    //     this.rollNumber = rollNumber;
    // }


    @Override
    public String toString() {
        return "Student {" +
               "Roll Number=" + rollNumber +
               ", Name='" + name + '\'' +
               ", Grade='" + grade + '\'' +
               ", Contact='" + contactNumber + '\'' +
               '}';
    }

    // Optional: equals and hashCode if you need to compare Student objects
    // based on content, especially if storing in Sets or using as HashMap keys
    // directly (though here we use rollNumber as the key).
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return rollNumber == student.rollNumber;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(rollNumber);
    }
}