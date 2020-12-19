package com.barhan.practice.springboot.dao.impl;

import com.barhan.practice.springboot.dao.DataAccessService;
import com.barhan.practice.springboot.dao.constants.StudentConstants;
import com.barhan.practice.springboot.dao.mapper.StudentRowMapper;
import com.barhan.practice.springboot.model.Student;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentDataAccessService implements DataAccessService<Student> {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public StudentDataAccessService(JdbcTemplate jdbcTemplate,
                                    @Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(StudentConstants.TABLE_NAME);
    }

    @Override
    public List<Student> selectAll() {
        String sql = String.format("SELECT * FROM %s", StudentConstants.TABLE_NAME);
        return jdbcTemplate.query(sql, new StudentRowMapper());
    }

    @Override
    public void update(Long id, Student student) {
        String sql = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=? where id=?",
                StudentConstants.TABLE_NAME,
                StudentConstants.FIRST_NAME_COLUMN,
                StudentConstants.LAST_NAME_COLUMN,
                StudentConstants.EMAIL_COLUMN,
                StudentConstants.GENDER_COLUMN
        );
        this.jdbcTemplate.update(sql,
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getGender().toString(),
                id
        );
    }

    @Override
    public void create(Student student) {
        Map<String, Object> params = new HashMap<>();
        params.put(StudentConstants.FIRST_NAME_COLUMN, student.getFirstName());
        params.put(StudentConstants.LAST_NAME_COLUMN, student.getLastName());
        params.put(StudentConstants.EMAIL_COLUMN, student.getEmail());
        params.put(StudentConstants.GENDER_COLUMN, student.getGender());
        this.simpleJdbcInsert.execute(params);
    }

    @Override
    public void delete(Long id) {
        String sql = String.format("DELETE from %s where id = ?", StudentConstants.TABLE_NAME);
        this.jdbcTemplate.update(sql, id);
    }

    public boolean isEmailTaken(String email) {
        String sql = String.format(
                "SELECT count(*) FROM %s WHERE %s = ?",
                StudentConstants.TABLE_NAME,
                StudentConstants.EMAIL_COLUMN
        );
        return this.checkExists(sql, email);
    }

    @Override
    public boolean isStudentExistsById(Long id) {
        String sql = String.format(
                "SELECT count(*) FROM %s WHERE %s = ?",
                StudentConstants.TABLE_NAME,
                StudentConstants.ID_COLUMN
        );
        return this.checkExists(sql, id.toString());
    }

    private boolean checkExists(String sql, String arg) {
        Integer count = jdbcTemplate.queryForObject(
                sql, new Object[]{ arg }, Integer.class
        );
        return count != null && count > 0;
    }
}
