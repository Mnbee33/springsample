package com.example.springsample.login.controller;

import com.example.springsample.login.domain.model.User;
import com.example.springsample.login.domain.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {
    @Autowired
    RestService service;

    @GetMapping("/rest/get")
    public List<User> getUserMany() {
        return service.selectMany();
    }

    @GetMapping("/rest/get/{id:.+}")
    public User getUserOne(@PathVariable("id") String userId) {
        return service.selectOne(userId);
    }

    @PostMapping("/rest/insert")
    public String postUserOne(@RequestBody User user) {
        boolean hadInserted = service.insert(user);
        return editResult(hadInserted);
    }

    private String editResult(boolean isSuccessful) {
        return isSuccessful ? "{\"result\":\"ok\"}" : "{\"result\":\"error\"}";
    }

    @PutMapping("rest/update")
    public String putUserOne(@RequestBody User user) {
        boolean hadUpdated = service.update(user);
        return editResult(hadUpdated);
    }

    @DeleteMapping("rest/delete/{id:.+}")
    public String deleteUserOne(@PathVariable("id") String userId) {
        boolean hadDeleted = service.delete(userId);
        return editResult(hadDeleted);
    }
}
