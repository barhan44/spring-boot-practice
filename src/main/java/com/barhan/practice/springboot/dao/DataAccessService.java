package com.barhan.practice.springboot.dao;

import java.util.List;

public interface DataAccessService<T> {
    void create(T entity);

    List<T> selectAll();

    void update(Long id, T entity);

    void delete(Long id);

    boolean isEmailTaken(String email);

    boolean isStudentExistsById(Long id);
}
