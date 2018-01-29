package com.roldukhine.entity;

public interface PhoneService {
    void addPhone(Phone phone);
    Phone getPhone(long id);

    void addPhone(Phone phone, Employee employee);
}
