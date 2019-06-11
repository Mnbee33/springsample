package com.example.springsample.trySpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HelloService {
    @Autowired
    private HelloRepository helloRepository;

    public Employee findOne(int id) {
        Map<String, Object> map = helloRepository.findOne(id);
        return toEmployee(map);
    }

    private Employee toEmployee(Map<String, Object> map) {
        Employee employee = new Employee();
        employee.setEmployeeId((Integer) map.get("employee_id"));
        employee.setEmployeeName((String) map.get("employee_name"));
        employee.setAge((Integer) map.get("age"));
        return employee;
    }
}
