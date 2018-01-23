package com.getjavajob.roldukhine.jdbc;

import com.getjavajob.roldukhine.api.EmployeeDao;
import com.getjavajob.roldukhine.api.PhoneDao;
import com.getjavajob.roldukhine.entity.Employee;
import com.getjavajob.roldukhine.entity.Phone;
import com.getjavajob.roldukhine.entity.PhoneType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = {"classpath:dao-context.xml", "classpath:dao-context-override.xml"})
@ActiveProfiles("jdbc")
public class PhoneDaoJdbcImplTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private PhoneDao phoneDao;

    @Test
    void testInsert() {
        Phone phone = new Phone();
        phone.setType(PhoneType.HOME);
        phone.setNumber("+78124928438");
        phoneDao.insert(phone);
        long id = phone.getId();
        Phone searchPhone = phoneDao.get(id);
        Assert.assertEquals(searchPhone, phone);
    }

    @Test
    void testDelete() {
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
    void testGetPhoneListByEmployee() {
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

    @Test
    void testAddPhoneToEmployee() {
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
}
