package com.getjavajob.roldukhine.jpa;

import com.getjavajob.roldukhine.api.EmployeeDao;
import com.getjavajob.roldukhine.api.PhoneDao;
import com.getjavajob.roldukhine.entity.Employee;
import com.getjavajob.roldukhine.entity.Phone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PhoneDaoJpaImpl extends AbstractDaoJpaImpl<Phone> implements PhoneDao {

    private static final Logger logger = LoggerFactory.getLogger(PhoneDaoJpaImpl.class);

    @Autowired
    @Qualifier("employeeDaoJpaImpl")
    private EmployeeDao employeeDao;

    @Override
    public void insertPhoneToEmployee(Phone phone, Employee employee) {
        logger.trace("insertPhoneToEmployee - phone {}, employee {}", phone, employee);
        employee.addPhoneToPhoneList(phone);
        employeeDao.update(employee);
    }

    @Override
    public List<Phone> getPhoneListByEmployee(Employee employee) {
        logger.debug("getPhoneListByEmployee employee {}", employee);
        List<Phone> phoneList = employee.getPhoneList();
        logger.debug("phoneList", phoneList);
        return phoneList;
    }
}
