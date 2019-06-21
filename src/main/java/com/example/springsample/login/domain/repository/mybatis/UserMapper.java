package com.example.springsample.login.domain.repository.mybatis;

import com.example.springsample.login.domain.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO m_user(" +
            " user_id," +
            " password," +
            " user_name," +
            " birthday," +
            " age," +
            " marriage," +
            " role)" +
            " VALUES(" +
            " #{userId}," +
            " #{password}," +
            " #{userName}," +
            " #{birthday}," +
            " #{age}," +
            " #{marriage}," +
            " #{role})")
    boolean insert(User user);


    @Select("SELECT user_id AS userId," +
            " password," +
            " user_name AS userName," +
            " birthday," +
            " age," +
            " marriage," +
            " role" +
            " FROM m_user" +
            " WHERE user_id = #{userId}")
    User selectOne(String userId);

    @Select("SELECT user_id AS userId," +
            " password," +
            " user_name AS userName," +
            " birthday," +
            " age," +
            " marriage," +
            " role" +
            " FROM m_user")
    List<User> selectMany();

    @Update("UPDATE m_user SET" +
            " password = #{password}," +
            " user_name = #{userName}," +
            " birthday = #{birthday}," +
            " age = #{age}," +
            " marriage = #{marriage}" +
            " WHERE user_id = #{userId}")
    boolean updateOne(User user);

    @Delete("DELETE FROM m_user WHERE user_id = #{userId}")
    boolean deleteOne(String userId);
}
