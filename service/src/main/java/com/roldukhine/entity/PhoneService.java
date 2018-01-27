package com.roldukhine.entity;

import com.roldukhine.api.PhoneDao;

public interface PhoneService {
    void addPhone(Phone phone);
    Phone getPhone(long id);

    void addPhone(Phone phone, Employee employee);
}
