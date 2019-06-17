package com.example.springsample.login.domain.repository.jdbc;

import com.example.springsample.login.domain.model.User;
import com.example.springsample.login.domain.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository("UserDaoNamedJdbcImpl")
public class UserDaoNamedJdbcImpl implements UserDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public int count() throws DataAccessException {
        String sql = "SELECT COUNT(*) FROM m_user";
        SqlParameterSource params = new MapSqlParameterSource();
        return jdbc.queryForObject(sql, params, Integer.class);
    }

    @Override
    public int insertOne(User user) throws DataAccessException {
        String sql = "INSERT INTO m_user" +
                "(user_id," +
                " password," +
                " user_name," +
                " birthday," +
                " age," +
                " marriage," +
                "role)" +
                " VALUES(:userId," +
                " :password," +
                " :userName," +
                " :birthday," +
                " :age," +
                " :marriage," +
                " :role)";
        SqlParameterSource params = createParameterSource(user);
        return jdbc.update(sql, params);
    }

    private SqlParameterSource createParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("userId", user.getUserId())
                .addValue("password", user.getPassword())
                .addValue("userName", user.getUserName())
                .addValue("birthday", user.getBirthday())
                .addValue("age", user.getAge())
                .addValue("marriage", user.isMarriage())
                .addValue("role", user.getRole());
    }

    @Override
    public User selectOne(String userId) throws DataAccessException {
        String sql = "SELECT * FROM m_user WHERE user_id = :userId";
        SqlParameterSource params = createKeyParam(userId);
        return jdbc.queryForObject(sql, params, new UserRowMapper());
    }

    private SqlParameterSource createKeyParam(String userId) {
        return new MapSqlParameterSource()
                .addValue("userId", userId);
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
    public List<User> selectMany() throws DataAccessException {
        String sql = "SELECT * FROM m_user";
        SqlParameterSource params = new MapSqlParameterSource();
        List<Map<String, Object>> getList = jdbc.queryForList(sql, params);

        List<User> userList = getList.stream()
                .map(this::convertTo)
                .collect(Collectors.toList());

        return userList;

    }

    @Override
    public int updateOne(User user) throws DataAccessException {
        String sql = "UPDATE m_user SET " +
                " password = :password," +
                " user_name = :userName," +
                " birthday = :birthday," +
                " age = :age," +
                " marriage = :marriage" +
                " WHERE user_id = :userId";
        SqlParameterSource params = createParameterSource(user);
        return jdbc.update(sql, params);
    }

    @Override
    public int deleteOne(String userId) throws DataAccessException {
        String sql = "DELETE FROM m_user WHERE user_id = :userId";
        SqlParameterSource params = createKeyParam(userId);
        return jdbc.update(sql, params);
    }

    @Override
    public void userCsvOut() throws DataAccessException {
        String sql = "SELECT * FROM m_user";
        UserRowCallbackHandler handler = new UserRowCallbackHandler();
        jdbc.query(sql, handler);
    }
}
