package com.getjavajob.roldukhine.entity;

import com.getjavajob.roldukhine.api.PhoneDao;

public interface PhoneService {
    void addPhone(Phone phone);
    Phone getPhone(long id);

    void setPhoneDao(PhoneDao phoneDao);

    void addPhone(Phone phone, Employee employee);
}
