package com.example.springsample.login.domain.service;

import com.example.springsample.login.domain.model.User;
import com.example.springsample.login.domain.repository.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @MockBean(name = "UserDaoJdbcImpl")
    UserDao dao;

    @Autowired
    UserService service;

    @Test
    public void testInsertSuccessfully() {
        when(dao.insertOne(any())).thenReturn(1);

        User user = new User();
        assertTrue(service.insert(user));
    }

    @Test
    public void testInsertFailure() {
        when(dao.insertOne(any())).thenReturn(0);

        User user = new User();
        assertFalse(service.insert(user));
    }
}