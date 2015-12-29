package com.getjavajob.web06.roldukhine.jdbc;

import com.getjavajob.web06.roldukhine.entity.Employee;
import com.getjavajob.web06.roldukhine.InitialDatabaseScript;
import com.getjavajob.web06.roldukhine.entity.Phone;
import com.getjavajob.web06.roldukhine.entity.PhoneType;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class PhoneDaoTest {
    private PhoneDao phoneDao = PhoneDao.getInstance();
    private EmployeeDao employeeDao = EmployeeDao.getInstance();

    @BeforeClass
    public static void initScript() {
        InitialDatabaseScript.executeScript();
    }

    @Test
    public void testInsert() {
        Phone phone = new Phone();
        phone.setType(PhoneType.HOME);
        phone.setNumber("+78124928438");
        phoneDao.insert(phone);
        long id = phone.getId();
        Phone searchPhone = phoneDao.get(id);
        Assert.assertEquals(searchPhone, phone);
    }

    @Test
    public void testDelete() {
        Phone phone = new Phone();
        phone.setType(PhoneType.HOME);
        phone.setNumber("+78124928438");
        phoneDao.insert(phone);
        long id = phone.getId();
        phoneDao.delete(phone);
        Phone searchPhone = phoneDao.get(id);
        Assert.assertNull(searchPhone);
    }

    @Test
    public void testGetPhoneListByEmployee() {
        Employee employee = new Employee();
        employee.setLastName("Ivanov");
        employeeDao.insert(employee);

        Phone phone = new Phone();
        phone.setType(PhoneType.HOME);
        phone.setNumber("+78124928438");
        phoneDao.insert(phone);

        List<Phone> phoneListByEmployee = phoneDao.getPhoneListByEmployee(employee);
        Assert.assertEquals(0, phoneListByEmployee.size());
    }
}
