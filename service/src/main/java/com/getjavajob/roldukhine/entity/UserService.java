package com.getjavajob.roldukhine.entity;

import java.util.List;

public interface UserService {
    void addUser(User user);

    User getUser(long id);

    void updateUser(User user);

    void deleteUser(User user);

    List<User> getAll();

    User checkUser(String login, String password);
}
