package com.getjavajob.web06.roldukhine.phonebook.web.controller;

import com.getjavajob.web06.roldukhine.entity.Employee;
import com.getjavajob.web06.roldukhine.entity.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping(value = "/photo")
public class PhotoEmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("employeeId") long employeeId) {
        String name = ((CommonsMultipartFile) file).getFileItem().getName();
        if (!file.isEmpty()) {
            try {
                byte[] bytesFromRequestFile = file.getBytes();

                Employee employee = employeeService.getEmployee(employeeId);
                employeeService.updatePhoto(employee, bytesFromRequestFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "redirect:/employee/edit/" + employeeId;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteImageForEmployee(@RequestParam("employeeId") long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        employeeService.updatePhoto(employee, null);

        return "redirect:/employee/edit/" + employeeId;
    }
}
