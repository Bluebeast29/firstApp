package com.example.firstapp;

import com.example.firstapp.models.Course;
import com.example.firstapp.models.Department;
import com.example.firstapp.models.Faculty;
import com.example.firstapp.models.Session;
import com.example.firstapp.models.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockData {

    public static List<Department> getDepartments() {
        List<Department> departments = new ArrayList<>();
        departments.add(new Department("1", "Computer Science", "CS", 15, 450));
        departments.add(new Department("2", "Electrical Engineering", "EE", 12, 320));
        departments.add(new Department("3", "Business Administration", "BBA", 10, 280));
        return departments;
    }

    public static List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("1", "Introduction to Programming", "CS101", "Computer Science", 3, "Beginner", false, "An introductory course on programming using Python. Covers basic syntax, data structures, and algorithms.", Arrays.asList("None"), Arrays.asList("Fall 2024 (Dr. Alan Turing)", "Spring 2025 (Dr. Ada Lovelace)")));
        courses.add(new Course("2", "Data Structures", "CS201", "Computer Science", 4, "Intermediate", false, "Deep dive into data structures like trees, graphs, and hash tables.", Arrays.asList("CS101"), Arrays.asList("Fall 2024 (Dr. John von Neumann)")));
        courses.add(new Course("3", "Circuit Analysis", "EE101", "Electrical Engineering", 3, "Beginner", false, "Basic concepts of electrical circuits, Ohm's law, Kirchhoff's laws.", Arrays.asList("Math 101"), Arrays.asList("Fall 2024 (Dr. Nikola Tesla)")));
        return courses;
    }

    public static List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("1", "Alice Smith", "alice.smith@college.edu", "CS2024-001", "Computer Science", "2024", 3, Arrays.asList("CS201", "Math201"), "88%"));
        students.add(new Student("2", "Bob Johnson", "bob.johnson@college.edu", "CS2024-002", "Computer Science", "2024", 3, Arrays.asList("CS201", "CS101"), "92%"));
        students.add(new Student("3", "Charlie Brown", "charlie.brown@college.edu", "EE2024-015", "Electrical Engineering", "2024", 1, Arrays.asList("EE101"), "75%"));
        return students;
    }

    public static List<Faculty> getFaculty() {
        List<Faculty> faculty = new ArrayList<>();
        faculty.add(new Faculty("1", "Dr. Alan Turing", "alan.turing@college.edu", "Computer Science", "Professor", "Artificial Intelligence, Cryptography", Arrays.asList("CS101", "CS301")));
        faculty.add(new Faculty("2", "Dr. Ada Lovelace", "ada.lovelace@college.edu", "Computer Science", "Assistant Professor", "Programming Languages", Arrays.asList("CS101")));
        return faculty;
    }

    public static List<Session> getSessions() {
        List<Session> sessions = new ArrayList<>();
        sessions.add(new Session("1", "2024-10-25", "09:00 AM", "10:30 AM", "CS101 - Introduction to Programming", "Dr. Alan Turing", "Room 101, Building A", "Upcoming"));
        sessions.add(new Session("2", "2024-10-25", "11:00 AM", "12:30 PM", "CS201 - Data Structures", "Dr. John von Neumann", "Room 205, Building A", "Ongoing"));
        sessions.add(new Session("3", "2024-10-24", "02:00 PM", "03:30 PM", "EE101 - Circuit Analysis", "Dr. Nikola Tesla", "Lab 1, Building B", "Completed"));
        return sessions;
    }
}
