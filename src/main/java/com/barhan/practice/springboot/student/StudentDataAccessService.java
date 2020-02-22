package com.barhan.practice.springboot.student;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class StudentDataAccessService {

    List<Student> selectAllStudents() {
        List<Student> students = new ArrayList<Student>();
        students.add(this.generateRandomStudent());
        students.add(this.generateRandomStudent());

        return students;
    }

    private Student generateRandomStudent() {
        String firstName = "Test" + (int) (Math.random() * 100);
        String lastName = "Name" + (int) (Math.random() * 100);
        String email = firstName + lastName + "@" + "test.com";
        return new Student(UUID.randomUUID(), firstName, lastName, email, Student.Gender.MALE);
    }
}
