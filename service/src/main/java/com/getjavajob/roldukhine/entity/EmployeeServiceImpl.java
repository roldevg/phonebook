package com.getjavajob.roldukhine.entity;

import com.getjavajob.roldukhine.api.EmployeeDao;
import com.getjavajob.roldukhine.api.PhoneDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private PhoneServiceImpl phoneServiceImpl;

    public void setEmployeeDao(EmployeeDao employeeDao) {
        logger.debug("setEmployeeDao {}", employeeDao);
        this.employeeDao = employeeDao;
    }

    @Transactional
    public void addEmployee(Employee employee) {
        logger.debug("addEmployee {}", employee);
        employeeDao.insert(employee);

        List<Phone> phoneList = employee.getPhoneList();
        logger.debug("phoneList {}", phoneList);
        for (Phone phone : phoneList) {
            phoneServiceImpl.addPhone(phone);
            addPhoneToEmployee(employee, phone);
        }
    }

    public Employee getEmployee(long id) {
        logger.debug("getEmployee {}", id);
        return employeeDao.get(id);
    }

    @Transactional
    public void updateEmployee(Employee employee) {
        logger.debug("updateEmployee {}", employee);
        employeeDao.update(employee);
    }

    @Transactional
    public void delete(long id) {
        logger.debug("delete by id {}", id);
        employeeDao.delete(id);
    }

    public List<Employee> getAll() {
        logger.debug("getAll");
        return employeeDao.getAll();
    }

    @Transactional
    public void addPhoneToEmployee(Employee employee, Phone phone) {
        logger.debug("addPhoneToEmployee, employee {}, phone {}", employee, phone);
        phoneDao.insertPhoneToEmployee(phone, employee);
    }

    @Transactional
    public void updatePhoto(Employee employee, byte[] photo) {
        logger.debug("updatePhoto, employee {}, photo {}", employee, photo);
        employeeDao.updatePhoto(employee, photo);
    }
}
