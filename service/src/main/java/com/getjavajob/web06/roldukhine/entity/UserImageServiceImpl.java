package com.getjavajob.web06.roldukhine.entity;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;


@Service
@Transactional(readOnly = true)
public class UserImageServiceImpl implements UserImageService {

    private static final String DEFAULT_USER_IMAGE = "defaultUserImage.png";

    public byte[] getDefaultImageUser() {
        try {
            InputStream in = getClass().getClassLoader().getResourceAsStream(DEFAULT_USER_IMAGE);
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getPhotoEmployee(Employee employee) {
        byte[] photo = employee.getPhoto();
        if (photo == null) {
            photo = getDefaultImageUser();
        }
        String photoBase64 = new Base64().encodeAsString(photo);
        return "data:image/png;base64," + photoBase64;
    }
}
