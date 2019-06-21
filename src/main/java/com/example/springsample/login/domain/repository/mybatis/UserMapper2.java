package com.example.springsample.login.domain.repository.mybatis;

import com.example.springsample.login.domain.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper2 {
    boolean insert(User user);

    User selectOne(String userId);

    List<User> selectMany();

    boolean updateOne(User user);

    boolean deleteOne(String userId);
}
