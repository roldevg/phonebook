package com.getjavajob.web06.roldukhine.entity;

import java.util.List;

public interface EmployeeService {
    void addEmployee(Employee employee);
    Employee getEmployee(long id);
    void updateEmployee(Employee employee);
    void delete(long id);
    List<Employee> getAll();
    void addPhoneToEmployee(Employee employee, Phone phone);
    void updatePhoto(Employee employee, byte[] photo);
}
