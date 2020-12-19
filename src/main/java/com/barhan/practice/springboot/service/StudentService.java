package com.barhan.practice.springboot.service;

import com.barhan.practice.springboot.model.Student;

import java.util.List;

public interface StudentService {
    void createNewStudent(Student student);

    List<Student> getAllStudents();

    void updateStudent(Long id, Student student);

    void deleteStudent(Long id);
}
