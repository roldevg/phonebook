package com.getjavajob.web06.roldukhine.entity;

import com.getjavajob.web06.roldukhine.ConnectionPool;
import com.getjavajob.web06.roldukhine.jdbc.EmployeeDao;
import com.getjavajob.web06.roldukhine.jdbc.PhoneDao;

import java.sql.Connection;
import java.sql.SQLException;

public class EmployeeService {
    private EmployeeDao employeeDao = EmployeeDao.getInstance();
    private PhoneDao phoneDao = PhoneDao.getInstance();

    public void addEmployee(Employee employee) {
        employeeDao.insert(employee);
    }

    public Employee getEmployee(long id) {
        return employeeDao.get(id);
    }

    public void addPhoneToEmployee(Employee employee, Phone phone) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            try {
                phoneDao.insert(phone);
                phoneDao.insertPhoneToEmployee(phone, employee);
            } catch (Exception e) {
                connection.rollback();
            } finally {
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
