package com.getjavajob.web06.roldukhine.phonebook.web.controller;

import com.getjavajob.web06.roldukhine.entity.Employee;
import com.getjavajob.web06.roldukhine.entity.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RootController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView root() {
        List<Employee> employeeList = employeeService.getAll();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("employeeList", employeeList);
        return modelAndView;
    }
}
