package com.example.springsample.login.domain.repository.jdbc;

import com.example.springsample.login.domain.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getString("user_id"));
        user.setPassword(resultSet.getString("password"));
        user.setUserName(resultSet.getString("user_name"));
        user.setBirthday(resultSet.getDate("birthday"));
        user.setAge(resultSet.getInt("age"));
        user.setMarriage(resultSet.getBoolean("marriage"));
        user.setRole(resultSet.getString("role"));
        return user;
    }
}
