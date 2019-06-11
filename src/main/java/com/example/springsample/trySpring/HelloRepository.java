package com.example.springsample.trySpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class HelloRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<String, Object> findOne(int id) {
        String query = "SELECT"
                + " employee_id,"
                + " employee_name,"
                + " age"
                + " FROM employee"
                + " WHERE employee_id=?";
        return jdbcTemplate.queryForMap(query, id);
    }
}
