package com.barhan.practice.springboot.service.impl;

import com.barhan.practice.springboot.dao.DataAccessService;
import com.barhan.practice.springboot.exception.ApiRequestException;
import com.barhan.practice.springboot.model.Student;
import com.barhan.practice.springboot.service.StudentService;
import com.barhan.practice.springboot.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final DataAccessService<Student> studentDataAccessService;
    private final EmailValidator emailValidator;

    @Autowired
    public StudentServiceImpl(DataAccessService<Student> studentDataAccessService,
                              EmailValidator emailValidator) {
        this.studentDataAccessService = studentDataAccessService;
        this.emailValidator = emailValidator;
    }

    public List<Student> getAllStudents() {
        return this.studentDataAccessService.selectAll();
    }

    @Override
    public void updateStudent(Long id, Student student) {
        if (!this.studentDataAccessService.isStudentExistsById(id)) {
            throw new ApiRequestException("Student does not exist!");
        }
        if (!this.emailValidator.test(student.getEmail())) {
            throw new ApiRequestException(String.format("%s is not valid!", student.getEmail()));
        }
        this.studentDataAccessService.update(id, student);
    }

    @Override
    public void createNewStudent(Student student) {
        if (this.studentDataAccessService.isEmailTaken(student.getEmail())) {
            throw new ApiRequestException(String.format("%s is already taken!", student.getEmail()));
        }
        if (!this.emailValidator.test(student.getEmail())) {
            throw new ApiRequestException(String.format("%s is not valid!", student.getEmail()));
        }
        this.studentDataAccessService.create(student);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!this.studentDataAccessService.isStudentExistsById(id)) {
            throw new ApiRequestException("Student does not exist!");
        }
        this.studentDataAccessService.delete(id);
    }
}
