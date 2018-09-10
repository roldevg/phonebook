package com.roldukhine.entity;

import java.util.Optional;

public interface UserService {
    void addUser(User user);

    User getUser(long id);

    void updateUser(User user);

    void deleteUser(User user);

    User checkUser(String login, String password);

    Optional<User> findByLogin(String login);
}
