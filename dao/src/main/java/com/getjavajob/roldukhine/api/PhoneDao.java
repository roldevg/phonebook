package com.getjavajob.roldukhine.api;

import com.getjavajob.roldukhine.entity.Employee;
import com.getjavajob.roldukhine.entity.Phone;

import java.util.List;

public interface PhoneDao extends CrudDao<Phone> {
    void insertPhoneToEmployee(Phone phone, Employee employee);

    List<Phone> getPhoneListByEmployee(Employee employee);
}
