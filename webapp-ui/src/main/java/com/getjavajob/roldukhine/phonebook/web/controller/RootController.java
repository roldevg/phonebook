package com.getjavajob.roldukhine.phonebook.web.controller;

import com.getjavajob.roldukhine.entity.Employee;
import com.getjavajob.roldukhine.entity.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RootController {

    private static final Logger logger = LoggerFactory.getLogger(RootController.class);

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView root() {
        logger.debug("call root mapping");
        List<Employee> employeeList = employeeService.getAll();
        ModelAndView modelAndView = new ModelAndView("index");
        logger.debug("employeeList {}", employeeList);
        modelAndView.addObject("employeeList", employeeList);
        return modelAndView;
    }
}
