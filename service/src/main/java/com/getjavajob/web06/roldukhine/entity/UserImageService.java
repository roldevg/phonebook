package com.getjavajob.web06.roldukhine.entity;

public interface UserImageService {
    byte[] getDefaultImageUser();

    String getPhotoEmployee(Employee employee);
}