package com.getjavajob.web06.roldukhine.entity;

import com.getjavajob.web06.roldukhine.api.PhoneDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PhoneServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(PhoneServiceImpl.class);

    @Autowired
    private PhoneDao phoneDao;

    @Transactional
    public void addPhone(Phone phone) {
        logger.debug("addPhone {}", phone);
        phoneDao.insert(phone);
    }

    public Phone getPhone(long id) {
        logger.debug("getPhone {}", id);
        return phoneDao.get(id);
    }
}
