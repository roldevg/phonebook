package com.getjavajob.web06.roldukhine.jdbc;

import com.getjavajob.web06.roldukhine.api.EmployeeDao;
import com.getjavajob.web06.roldukhine.entity.Employee;
import com.getjavajob.web06.roldukhine.entity.Phone;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = {"classpath:dao-context.xml", "classpath:dao-context-override.xml"})
@ActiveProfiles("jdbc")
public class EmployeeDaoJdbcImplTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    public void testInsert() throws Exception {
        Employee employee = createTestEmployee();
        employeeDao.insert(employee);
        Assert.assertNotEquals(0, employee.getId());
    }

    @Test
    public void testGetAllWithHibernate() {
        employeeDao.getAll();
    }

    @Test
    public void testSelect() throws Exception {
        Employee employee = createTestEmployee();
        employee.setPhoneList(new ArrayList<Phone>());
        employeeDao.insert(employee);
        long id = employee.getId();
        employeeDao.get(id);
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
    public void testUpdatePhoto() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("Petrov");
        employee.setSecondName("Petrov");
        employee.setLastName("Petrov");
        employeeDao.insert(employee);
        byte[] photo = new byte[10];
        employeeDao.updatePhoto(employee, photo);
        Employee employeeAfterUpdate = employeeDao.get(employee.getId());
        Assert.assertEquals(employeeAfterUpdate, employee);
    }

    private Employee createTestEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Ivan");
        employee.setSecondName("Petrovich");
        employee.setLastName("Petrov");
        employee.setEmail("ivan@petrov.ru");
        employee.setWorkAddress("SPB");
        employee.setHomeAddress("Moscow");
        employee.setSkype("petrovSkype");
        employee.setNote("note");
        return employee;
    }
}