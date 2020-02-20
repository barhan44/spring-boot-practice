package com.barhan.practice.springboot.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("students")
public class StudentController {

    @GetMapping
    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<Student>();
        students.add(this.generateRandomStudent());
        students.add(this.generateRandomStudent());

        return students;
    }

    private Student generateRandomStudent() {
            String firstName = "Test" + (int) ( Math.random() * 100 );
            String lastName = "Name" + (int) ( Math.random() * 100 );
            String email = firstName + lastName + "@" + "test.com";
            return new Student(UUID.randomUUID(), firstName, lastName, email, Student.Gender.MALE);
    }

}
