package com.example.springsample.login.domain.repository.jdbc;

import com.example.springsample.login.domain.model.User;
import com.example.springsample.login.domain.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public int count() throws DataAccessException {
        int count = jdbc.queryForObject("SELECT COUNT(*) FROM m_user", Integer.class);
        return count;
    }

    @Override
    public int insertOne(User user) throws DataAccessException {
        int rowNumber = jdbc.update(
                "INSERT INTO m_user(user_id," +
                        " password," +
                        " user_name," +
                        " birthday," +
                        " age," +
                        " marriage," +
                        "role)" +
                        " VALUES(?, ?, ?, ?, ?, ?, ?)"
                , user.getUserId()
                , user.getPassword()
                , user.getUserName()
                , user.getBirthday()
                , user.getAge()
                , user.isMarriage()
                , user.getRole());
        return rowNumber;
    }

    @Override
    public User selectOne(String userId) throws DataAccessException {
        Map<String, Object> map = jdbc.queryForMap(
                "SELECT * FROM m_user" +
                        " WHERE user_id = ?"
                , userId
        );
        return convertTo(map);
    }

    @Override
    public List<User> selectMany() throws DataAccessException {
        List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM m_user");

        List<User> userList = getList.stream()
                .map(this::convertTo)
                .collect(Collectors.toList());

        return userList;
    }

    private User convertTo(Map<String, Object> resultMap) {
        User user = new User();
        user.setUserId((String) resultMap.get("user_id"));
        user.setPassword((String) resultMap.get("password"));
        user.setUserName((String) resultMap.get("user_name"));
        user.setBirthday((Date) resultMap.get("birthday"));
        user.setAge((Integer) resultMap.get("age"));
        user.setMarriage((Boolean) resultMap.get("marriage"));
        user.setRole((String) resultMap.get("role"));
        return user;
    }

    @Override
    public int updateOne(User user) throws DataAccessException {
        int rowNumber = jdbc.update(
                "UPDATE m_user SET " +
                        " password = ?," +
                        " user_name = ?," +
                        " birthday = ?," +
                        " age = ?," +
                        " marriage = ?"
                , user.getPassword()
                , user.getUserName()
                , user.getBirthday()
                , user.getAge()
                , user.isMarriage());

/*
        if (rowNumber > 0) {
            throw new DataAccessException("Transaction Test") {
            };
        }
*/
        return rowNumber;

    }

    @Override
    public int deleteOne(String userId) throws DataAccessException {
        int rowNumber = jdbc.update("DELETE FROM m_user WHERE user_id = ?", userId);
        return rowNumber;
    }

    @Override
    public void userCsvOut() throws DataAccessException {
        String sql = "SELECT * FROM m_user";
        UserRowCallbackHandler handler = new UserRowCallbackHandler();
        jdbc.query(sql, handler);
    }
}
