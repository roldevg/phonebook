package com.getjavajob.web06.roldukhine.entity;

import com.getjavajob.web06.roldukhine.api.EmployeeDao;
import com.getjavajob.web06.roldukhine.api.PhoneDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private PhoneService phoneService;

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Transactional
    public void addEmployee(Employee employee) {
        employeeDao.insert(employee);

        List<Phone> phoneList = employee.getPhoneList();
        for (Phone phone : phoneList) {
            phoneService.addPhone(phone);
            addPhoneToEmployee(employee, phone);
        }
    }

    public Employee getEmployee(long id) {
        return employeeDao.get(id);
    }

    @Transactional
    public void updateEmployee(Employee employee) {
        employeeDao.update(employee);
    }

    @Transactional
    public void delete(long id) {
        employeeDao.delete(id);
    }

    public List<Employee> getAll() {
        return employeeDao.getAll();
    }

    @Transactional
    public void addPhoneToEmployee(Employee employee, Phone phone) {
        phoneDao.insertPhoneToEmployee(phone, employee);
    }

    @Transactional
    public void updatePhoto(Employee employee, byte[] photo) {
        employeeDao.updatePhoto(employee, photo);
    }
}
