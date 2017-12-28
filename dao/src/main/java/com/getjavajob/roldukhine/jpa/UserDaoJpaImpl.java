package com.getjavajob.roldukhine.jpa;

import com.getjavajob.roldukhine.api.UserDao;
import com.getjavajob.roldukhine.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoJpaImpl extends AbstractDaoJpaImpl<User> implements UserDao {

}
