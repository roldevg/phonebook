package com.roldukhine.entity;

import com.roldukhine.api.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Transactional
    public void addUser(User user) {
        logger.debug("addUser {}", user);
        userDao.insert(user);
    }

    public User getUser(long id) {
        logger.debug("getUser {}", id);
        return userDao.get(id);
    }

    @Transactional
    public void updateUser(User user) {
        logger.debug("updateUser {}", user);
        userDao.update(user);
    }

    @Transactional
    public void deleteUser(User user) {
        logger.debug("deleteUser {}", user);
        userDao.delete(user);
    }

    public List<User> getAll() {
        logger.debug("getAll");
        return userDao.getAll();
    }

    public User checkUser(String login, String password) {
        logger.debug("checkUser, login {}, password {}");
        List<User> userList = getAll();
        for (User user : userList) {
            if (user.getLogin().equals(login) &&
                    user.getPassword().equals(password)) {
                logger.debug("user by login and password {}", user);
                return user;
            }
        }

        logger.debug("cannot find user by login and password");
        return null;
    }
}
