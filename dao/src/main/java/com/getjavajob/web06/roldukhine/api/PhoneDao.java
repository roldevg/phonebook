package com.getjavajob.web06.roldukhine.api;

import com.getjavajob.web06.roldukhine.entity.Employee;
import com.getjavajob.web06.roldukhine.entity.Phone;

import java.util.List;

public interface PhoneDao extends CrudDao<Phone> {
    void insertPhoneToEmployee(Phone phone, Employee employee);

    List<Phone> getPhoneListByEmployee(Employee employee);
}
