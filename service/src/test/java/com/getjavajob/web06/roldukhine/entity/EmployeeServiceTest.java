package com.getjavajob.web06.roldukhine.entity;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class EmployeeServiceTest {

    @Test
    @Ignore
    public void testAddEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setLastName("Ivanov");
        employee.setEmail("ivanov@gmail.com");

        EmployeeService service = new EmployeeService();
        service.addEmployee(employee);
        long expectedId = employee.getId();

        Employee searchEmployee = service.getEmployee(expectedId);
        Assert.assertEquals(searchEmployee, employee);
    }

    @Test
    public void testAddPhoneToEmployee() throws Exception {

    }
}