package com.example.springsample.login.domain.repository.jdbc;

import com.example.springsample.login.domain.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserResultSetExtractor implements ResultSetExtractor<List<User>> {
    @Override
    public List<User> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<User> userList = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setUserId(resultSet.getString("user_id"));
            user.setPassword(resultSet.getString("password"));
            user.setUserName(resultSet.getString("user_name"));
            user.setBirthday(resultSet.getDate("birthday"));
            user.setAge(resultSet.getInt("age"));
            user.setMarriage(resultSet.getBoolean("marriage"));
            user.setRole(resultSet.getString("role"));

            userList.add(user);
        }

        if (userList.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }

        return userList;
    }
}
