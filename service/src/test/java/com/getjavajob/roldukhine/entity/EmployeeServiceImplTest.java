package com.getjavajob.roldukhine.entity;

import com.getjavajob.roldukhine.api.EmployeeDao;
import com.getjavajob.roldukhine.api.PhoneDao;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class EmployeeServiceImplTest {

    private EmployeeServiceImpl service = new EmployeeServiceImpl();

    private PhoneService phoneService = mock(PhoneService.class);

    private EmployeeDao employeeDao = mock(EmployeeDao.class);
    private PhoneDao phoneDao = mock(PhoneDao.class);

    @Test
    public void testAddEmployee() throws Exception {
        phoneService.setPhoneDao(phoneDao);

        Employee employee = new Employee();
        employee.setLastName("Ivanov");
        employee.setEmail("ivanov@gmail.com");
        List<Phone> phoneList = new ArrayList<>();
        Phone phone = new Phone();
        phone.setNumber("+7921345");
        phone.setType(PhoneType.HOME);
        phoneList.add(phone);
        employee.setPhoneList(phoneList);

        service.setEmployeeDao(employeeDao);
        service.setPhoneService(phoneService);
        service.addEmployee(employee);

        verify(employeeDao, times(1)).insert(employee);
        verify(phoneService, times(1)).addPhone(phone, employee);
    }
}
