package com.roldukhine.entity;

import com.roldukhine.api.UserDao;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public void addUser(@NonNull User user) {
        logger.debug("addUser {}", user);
        userDao.insert(user);
    }

    public User getUser(long id) {
        logger.debug("getUser {}", id);
        return userDao.get(id);
    }

    @Transactional
    public void updateUser(@NonNull User user) {
        logger.debug("updateUser {}", user);
        userDao.update(user);
    }

    @Transactional
    public void deleteUser(@NonNull User user) {
        logger.debug("deleteUser {}", user);
        userDao.delete(user);
    }

    public User checkUser(@NonNull String login, @NonNull String password) {
        logger.debug("checkUser, login {}, password {}", login, password);

        List<User> userList = userDao.getAll();
        logger.debug("getAll {}", userList);

        User user = userList.stream()
                .filter(userItem -> userItem.getLogin().equals(login) && userItem.getPassword().equals(password))
                .findFirst()
                .orElse(null);
        logger.debug("user by login and password {}", user);
        return user;
    }

    @Override
    public Optional<User> findByLogin(@NonNull String login) {
        List<User> users = userDao.getAll();

        return users.stream()
                .filter(it -> it.getLogin().equals(login))
                .findFirst();
    }
}
