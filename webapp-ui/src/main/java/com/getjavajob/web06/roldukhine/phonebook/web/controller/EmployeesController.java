package com.getjavajob.web06.roldukhine.phonebook.web.controller;

import com.getjavajob.web06.roldukhine.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping(value = "/employee")
public class EmployeesController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeesController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private UserImageService userImageService;

    @RequestMapping(value = {"/getAll"}, method = RequestMethod.GET)
    public ModelAndView getAllEmployees() {
        List<Employee> employeeList = employeeService.getAll();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("employeeList", employeeList);
        logger.info("add object: %s", employeeList);
        return modelAndView;
    }

    @RequestMapping(value = "/edit/{employeeId}", method = RequestMethod.GET)
    public ModelAndView editEmployee(@PathVariable("employeeId") long employeeId) throws IOException, URISyntaxException {
        logger.info("edit employee with id %s", employeeId);
        Employee employee = employeeService.getEmployee(employeeId);
        ModelAndView modelAndView = new ModelAndView("editEmployee");

        modelAndView.addObject("employee", employee);

        String photoEmployee = userImageService.getPhotoEmployee(employee);
        modelAndView.addObject("photo", photoEmployee);

        return modelAndView;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insertEmployee(@ModelAttribute Employee employee) {
        employeeService.addEmployee(employee);
        return "redirect:/employee/getAll";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    protected String saveEmployee(@ModelAttribute Employee employee) {
        employeeService.updateEmployee(employee);

        return "redirect:/employee/getAll";
    }

    @RequestMapping(value = "/delete/{employeeId}", method = RequestMethod.POST)
    public String deleteEmployee(@PathVariable("employeeId") long employeeId) {
        employeeService.delete(employeeId);
        return "redirect:/employee/getAll";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addEmployee() {
        return "addEmployee";
    }
}
