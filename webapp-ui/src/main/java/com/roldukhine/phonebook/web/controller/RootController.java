package com.roldukhine.phonebook.web.controller;

import com.roldukhine.entity.Employee;
import com.roldukhine.entity.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;

@Configuration
@EnableAutoConfiguration
@Slf4j
@Controller
public class RootController {

    private final EmployeeService employeeService;

    @Autowired
    public RootController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping("/")
    public ModelAndView root(OAuth2Authentication authentication) {
        printAuthInfo(authentication);

        logger.debug("call root mapping");
        List<Employee> employeeList = employeeService.getAll();
        ModelAndView modelAndView = new ModelAndView("index");
        logger.debug("employeeList {}", employeeList);
        modelAndView.addObject("employeeList", employeeList);
        return modelAndView;
    }

    private void printAuthInfo(OAuth2Authentication authentication) {
        if (authentication != null) {
            LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>)
                    authentication.getUserAuthentication().getDetails();
            logger.debug("===========================================");
            logger.debug("Authentication Object is: " + properties);
            logger.debug("User name is: " + properties.get("name"));
            logger.debug("User ID is: " + properties.get("id"));
        }
    }

}
