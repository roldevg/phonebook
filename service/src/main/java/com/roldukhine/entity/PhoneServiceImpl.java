package com.roldukhine.entity;

import com.roldukhine.api.PhoneDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PhoneServiceImpl implements PhoneService {

    private static final Logger logger = LoggerFactory.getLogger(PhoneServiceImpl.class);

    @Autowired
    private PhoneDao phoneDao;

    @Transactional
    public void addPhone(Phone phone) {
        logger.debug("addPhone {}", phone);
        phoneDao.insert(phone);
    }

    @Transactional
    @Override
    public void addPhone(Phone phone, Employee employee) {
        addPhone(phone);

        logger.debug("addPhoneToEmployee, employee {}, phone {}", employee, phone);
        phoneDao.insertPhoneToEmployee(phone, employee);
    }

    public Phone getPhone(long id) {
        logger.debug("getPhone {}", id);
        return phoneDao.get(id);
    }

    @Override
    public void setPhoneDao(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }
}
