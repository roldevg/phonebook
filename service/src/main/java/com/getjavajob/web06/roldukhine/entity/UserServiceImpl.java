package com.getjavajob.web06.roldukhine.entity;

import com.getjavajob.web06.roldukhine.api.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    public void addUser(User user) {
        userDao.insert(user);
    }

    public User getUser(long id) {
        return userDao.get(id);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void deleteUser(User user) {
        userDao.delete(user);
    }

    public List<User> getAll() {
        return userDao.getAll();
    }

    public User checkUser(String login, String password) {
        List<User> userList = getAll();
        for (User user : userList) {
            if (user.getLogin().equals(login) &&
                    user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
