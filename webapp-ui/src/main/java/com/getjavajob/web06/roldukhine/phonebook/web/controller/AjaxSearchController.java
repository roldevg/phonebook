package com.getjavajob.web06.roldukhine.phonebook.web.controller;

import com.getjavajob.web06.roldukhine.api.EmployeeDao;
import com.getjavajob.web06.roldukhine.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AjaxSearchController {

    private static final Logger logger = LoggerFactory.getLogger(AjaxSearchController.class);
    @Autowired
    private EmployeeDao employeeDao;

    @RequestMapping(value = {"/ajax/search"}, method = RequestMethod.POST)
    public
    @ResponseBody
    List<String> getCity(@RequestParam("query") String search) throws IOException {

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

        return employees;
    }
}
