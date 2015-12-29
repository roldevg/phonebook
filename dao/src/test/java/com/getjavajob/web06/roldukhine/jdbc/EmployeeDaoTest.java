package com.getjavajob.web06.roldukhine.jdbc;

import com.getjavajob.web06.roldukhine.entity.Employee;
import com.getjavajob.web06.roldukhine.InitialDatabaseScript;
import com.getjavajob.web06.roldukhine.entity.Phone;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class EmployeeDaoTest {

    private EmployeeDao employeeDao = EmployeeDao.getInstance();

    @BeforeClass
    public static void initScript() {
        InitialDatabaseScript.executeScript();
    }

    @Test
    public void testGetTableName() throws Exception {
        Assert.assertEquals("Employee", employeeDao.getTableName());
    }

    @Test
    public void testInsert() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("Petrov");
        employee.setSecondName("Petrov");
        employee.setLastName("Petrov");

        employee.setDepartment(null);
        employeeDao.insert(employee);
        Assert.assertEquals("Petrov", employee.getFirstName());
        employeeDao.delete(employee);
    }

    @Test
    public void testDelete() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("Petrov");
        employee.setSecondName("Petrov");
        employee.setLastName("Petrov");
        employeeDao.insert(employee);
        employeeDao.delete(employee);
    }

    @Test
    public void testUpdate() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("Petrov");
        employee.setSecondName("Petrov");
        employee.setLastName("Petrov");
        employee.setDepartment(null);
        employeeDao.insert(employee);
        Assert.assertEquals("Petrov", employee.getLastName());
        employeeDao.update(employee);
        employeeDao.delete(employee);
    }

    @Test
    public void testSelect() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("Petrov");
        employee.setSecondName("Petrov");
        employee.setLastName("Petrov");
        employee.setDepartment(null);
        employee.setPhoneList(new ArrayList<Phone>());
        employeeDao.insert(employee);
        long id = employee.getId();
        Employee selectEmployee = employeeDao.get(id);
        Assert.assertEquals(selectEmployee, employee);
    }
}