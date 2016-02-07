package com.getjavajob.web06.roldukhine.jpa;

import com.getjavajob.web06.roldukhine.api.EmployeeDao;
import com.getjavajob.web06.roldukhine.api.PhoneDao;
import com.getjavajob.web06.roldukhine.entity.Employee;
import com.getjavajob.web06.roldukhine.entity.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class PhoneDaoJpaImpl extends AbstractDaoJpaImpl<Phone> implements PhoneDao {

    @Autowired
    @Qualifier("employeeDaoJpaImpl")
    private EmployeeDao employeeDao;

    @Override
    @Transactional
    public void insertPhoneToEmployee(Phone phone, Employee employee) {
        employee.addPhoneToPhoneList(phone);
        employeeDao.update(employee);
    }

    @Override
    public List<Phone> getPhoneListByEmployee(Employee employee) {
        return employee.getPhoneList();
    }
}