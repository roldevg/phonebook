package com.getjavajob.web06.roldukhine.jpa;

import com.getjavajob.web06.roldukhine.api.UserDao;
import com.getjavajob.web06.roldukhine.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class UserDaoJpaImpl extends AbstractDaoJpaImpl<User> implements UserDao {

}
