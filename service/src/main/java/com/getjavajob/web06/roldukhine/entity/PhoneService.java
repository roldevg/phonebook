package com.getjavajob.web06.roldukhine.entity;

import com.getjavajob.web06.roldukhine.api.PhoneDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PhoneService {

    private static final Logger logger = LoggerFactory.getLogger(PhoneService.class);

    @Autowired
    private PhoneDao phoneDao;

    @Transactional
    public void addPhone(Phone phone) {
        phoneDao.insert(phone);
    }

    public Phone getPhone(long id) {
        return phoneDao.get(id);
    }
}
