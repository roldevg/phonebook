package com.getjavajob.web06.roldukhine.jdbc;

import com.getjavajob.web06.roldukhine.api.DepartmentDao;
import com.getjavajob.web06.roldukhine.api.EmployeeDao;
import com.getjavajob.web06.roldukhine.entity.BaseEntity;
import com.getjavajob.web06.roldukhine.entity.Department;
import com.getjavajob.web06.roldukhine.entity.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = {"classpath:dao-context.xml", "classpath:dao-context-override.xml"})
public class DepartmentDaoTest {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private EmployeeDao employeeDao;

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
        departmentDao.delete(department);
        BaseEntity baseEntity = departmentDao.get(id);
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
