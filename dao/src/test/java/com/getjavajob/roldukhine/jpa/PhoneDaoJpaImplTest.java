package com.getjavajob.roldukhine.jpa;

import com.getjavajob.roldukhine.api.EmployeeDao;
import com.getjavajob.roldukhine.api.PhoneDao;
import com.getjavajob.roldukhine.entity.Employee;
import com.getjavajob.roldukhine.entity.Phone;
import com.getjavajob.roldukhine.entity.PhoneType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = {"classpath:dao-context.xml", "classpath:dao-context-override.xml"})
@ActiveProfiles(value = "jpa")
public class PhoneDaoJpaImplTest {

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    @Transactional
    public void insertPhoneToEmployee() {
        Employee employee = new Employee();
        employee.setLastName("Ivanov");
        employeeDao.insert(employee);

        Phone phone = new Phone();
        phone.setType(PhoneType.HOME);
        phone.setNumber("+78124928438");
        phoneDao.insert(phone);

        phoneDao.insertPhoneToEmployee(phone, employee);
        List<Phone> phoneListByEmployee = phoneDao.getPhoneListByEmployee(employee);
        Assert.assertEquals(1, phoneListByEmployee.size());
    }

    @Test
    @Transactional
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
