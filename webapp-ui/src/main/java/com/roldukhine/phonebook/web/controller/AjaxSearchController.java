package com.roldukhine.phonebook.web.controller;

import com.roldukhine.api.EmployeeDao;
import com.roldukhine.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class AjaxSearchController {

    private final EmployeeDao employeeDao;

    @Autowired
    public AjaxSearchController(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @RequestMapping(value = {"/ajax/search"}, method = RequestMethod.POST)
    @ResponseBody
    public List<String> getCity(@RequestParam("query") String search) {
        logger.debug("getCity, search query {}", search);
        List<Employee> employeeList;
        List<Employee> all = employeeDao.getAll();
        if (search == null) {
            employeeList = employeeDao.getAll();
        } else {
            employeeList = new ArrayList<>();
            for (Employee employee : all) {
                if (employee.getLastName().toLowerCase().contains(search.toLowerCase())) {
                    employeeList.add(employee);
                }
            }
        }

        List<String> employees = new ArrayList<>();
        for (Employee employee : employeeList) {
            employees.add(employee.getLastName());
        }

        logger.debug("return employees {}", employees);

        return employees;
    }
}
