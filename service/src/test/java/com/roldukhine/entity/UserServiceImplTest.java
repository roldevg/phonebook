package com.roldukhine.entity;

import com.roldukhine.api.UserDao;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.Collections;

public class UserServiceImplTest {

    @Test
    public void shouldExistUser() {
        UserServiceImpl userService = new UserServiceImpl();
        UserDao userDaoMock = Mockito.mock(UserDao.class);
        userService.setUserDao(userDaoMock);

        Mockito.when(userDaoMock.getAll()).thenReturn(Collections.singletonList(createUser("admin", "admin")));

        Assertions.assertNotNull(userService.checkUser("admin", "admin"));
    }

    @Test
    public void shouldNotExistUser() {
        UserServiceImpl userService = new UserServiceImpl();
        UserDao userDaoMock = Mockito.mock(UserDao.class);
        userService.setUserDao(userDaoMock);

        Assertions.assertNull(userService.checkUser("admin", "admin"));
    }

    private User createUser(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return user;
    }
}
