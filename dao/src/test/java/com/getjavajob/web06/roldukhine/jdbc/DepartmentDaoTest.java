package com.getjavajob.web06.roldukhine.jdbc;

import com.getjavajob.web06.roldukhine.entity.BaseEntity;
import com.getjavajob.web06.roldukhine.entity.Department;
import com.getjavajob.web06.roldukhine.entity.Employee;
import com.getjavajob.web06.roldukhine.InitialDatabaseScript;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DepartmentDaoTest {
    private DepartmentDao departmentDao = DepartmentDao.getInstance();
    private EmployeeDao employeeDao = EmployeeDao.getInstance();

    @BeforeClass
    public static void initScript() {
        InitialDatabaseScript.executeScript();
    }

    @Test
    public void testInsert() {
        Department department = createDepartment();
        departmentDao.insert(department);
    }

    @Test
    public void testDelete() {
        Department department = createDepartment();
        departmentDao.insert(department);
        long id = department.getId();
        BaseEntity baseEntity = departmentDao.get(id);
        Assert.assertNotNull(baseEntity);
        departmentDao.delete(department);
        baseEntity = departmentDao.get(id);
        Assert.assertNull(baseEntity);
    }

    private Department createDepartment() {
        Employee employee = getManager();
        Department department = new Department();
        department.setName("IT");
        department.setManager(employee);
        return department;
    }

    private Employee getManager() {
        Employee employee = new Employee();
        employee.setFirstName("Petrov");
        employee.setSecondName("Petrov");
        employee.setLastName("Petrov");

        employee.setDepartment(null);
        employeeDao.insert(employee);
        return employee;
    }
}
