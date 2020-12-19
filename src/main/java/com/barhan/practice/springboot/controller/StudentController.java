package com.barhan.practice.springboot.controller;

import com.barhan.practice.springboot.model.Student;
import com.barhan.practice.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Void> addNewStudent(@RequestBody @Valid Student student) {
        this.studentService.createNewStudent(student);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(this.studentService.getAllStudents());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStudent(@PathVariable("id") Long id,
                                              @RequestBody @Valid Student student) {
        this.studentService.updateStudent(id, student);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long id) {
        this.studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
