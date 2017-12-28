package com.getjavajob.roldukhine.api;

import com.getjavajob.roldukhine.entity.Employee;

public interface EmployeeDao extends CrudDao<Employee> {

    void delete(long id);

    void updatePhoto(Employee employee, byte[] photo);
}
