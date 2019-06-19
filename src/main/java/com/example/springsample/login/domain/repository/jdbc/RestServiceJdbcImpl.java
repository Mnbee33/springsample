package com.example.springsample.login.domain.repository.jdbc;

import com.example.springsample.login.domain.model.User;
import com.example.springsample.login.domain.repository.UserDao;
import com.example.springsample.login.domain.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class RestServiceJdbcImpl implements RestService {
    @Autowired
    @Qualifier("UserDaoJdbcImpl")
    UserDao dao;

    @Override
    public boolean insert(User user) {
        int rowNumber = dao.insertOne(user);
        return rowNumber > 0;
    }

    @Override
    public User selectOne(String userId) {
        return dao.selectOne(userId);
    }

    @Override
    public List<User> selectMany() {
        return dao.selectMany();
    }

    @Override
    public boolean update(User user) {
        int rowNumber = dao.updateOne(user);
        return rowNumber > 0;
    }

    @Override
    public boolean delete(String userId) {
        int rowNumber = dao.deleteOne(userId);
        return rowNumber > 0;
    }
}
