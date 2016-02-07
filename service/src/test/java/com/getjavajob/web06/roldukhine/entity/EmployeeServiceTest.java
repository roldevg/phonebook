package com.getjavajob.web06.roldukhine.entity;

import com.getjavajob.web06.roldukhine.api.EmployeeDao;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;

public class EmployeeServiceTest {

    private EmployeeService service = new EmployeeService();

    private PhoneService phoneService = new PhoneService();

    private EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);

    @Test
    public void testAddEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setLastName("Ivanov");
        employee.setEmail("ivanov@gmail.com");

        service.setEmployeeDao(employeeDao);
        service.addEmployee(employee);
        long expectedId = employee.getId();

        Employee searchEmployee = service.getEmployee(expectedId);
        /*Assert.assertEquals(searchEmployee, employee);*/

        Mockito.verify(employeeDao, times(1)).insert(employee);
    }

    @Test
    public void testAddPhoneToEmployee() throws Exception {
        /*Employee employee = new Employee();
        employee.setLastName("Ivanov");
        employee.setEmail("ivanov@gmail.com");

        service.addEmployee(employee);

        Phone phone = new Phone();
        phone.setNumber("33333");
        phoneService.addPhone(phone);

        service.addPhoneToEmployee(employee, phone);
        List<Phone> phoneList = employee.getPhoneList();*/
        /*Assert.assertEquals(1, phoneList.size());*/
    }
}