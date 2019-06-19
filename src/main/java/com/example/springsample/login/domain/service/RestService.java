package com.example.springsample.login.domain.service;

import com.example.springsample.login.domain.model.User;

import java.util.List;

public interface RestService {
    boolean insert(User user);

    User selectOne(String userId);

    List<User> selectMany();

    boolean update(User user);

    boolean delete(String userId);
}
