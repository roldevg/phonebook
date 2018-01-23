package com.roldukhine.jdbc;

import com.roldukhine.api.UserDao;
import com.roldukhine.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = {"classpath:dao-context.xml", "classpath:dao-context-override.xml"})
@ActiveProfiles("jdbc")
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Test
    void testInsert() throws Exception {
        User user = createTestUser();
        userDao.insert(user);
        Assert.assertNotEquals(0, user.getId());
    }

    @Test
    void testSelect() throws Exception {
        User user = createTestUser();
        userDao.insert(user);
        Assert.assertEquals(userDao.get(user.getId()), user);
    }

    @Test
    void testDelete() throws Exception {
        User user = createTestUser();
        userDao.insert(user);
        userDao.delete(user);
        Assert.assertNull(userDao.get(user.getId()));
    }

    @Test
    void testUpdate() throws Exception {
        User user = createTestUser();
        userDao.insert(user);
        user.setLogin("admin");
        userDao.update(user);
        User userById = userDao.get(user.getId());
        Assert.assertEquals(user.getLogin(), userById.getLogin());
    }

    private User createTestUser() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        return user;
    }
}