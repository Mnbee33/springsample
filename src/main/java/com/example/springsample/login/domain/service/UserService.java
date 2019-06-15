package com.example.springsample.login.domain.service;

import com.example.springsample.login.domain.model.User;
import com.example.springsample.login.domain.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao dao;

    public boolean insert(User user) {
        int rowNumber = dao.insertOne(user);
        return isSuccessfulUpdate(rowNumber);
    }

    private boolean isSuccessfulUpdate(int rowNumber) {
        return rowNumber > 0;
    }

    public int count() {
        return dao.count();
    }

    public List<User> selectMany() {
        return dao.selectMany();
    }

    public User selectOne(String userId) {
        return dao.selectOne(userId);
    }

    public boolean updateOne(User user) {
        int rowNumber = dao.updateOne(user);
        return isSuccessfulUpdate(rowNumber);
    }

    public boolean deleteOne(String userId) {
        int rowNumber = dao.deleteOne(userId);
        return isSuccessfulUpdate(rowNumber);
    }
}
