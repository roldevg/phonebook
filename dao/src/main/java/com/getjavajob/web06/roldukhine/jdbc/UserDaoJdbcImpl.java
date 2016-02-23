package com.getjavajob.web06.roldukhine.jdbc;

import com.getjavajob.web06.roldukhine.api.UserDao;
import com.getjavajob.web06.roldukhine.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;

@Repository
public class UserDaoJdbcImpl extends AbstractDaoJdbcImpl<User> implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoJdbcImpl.class);

    private static final String TABLE_NAME = "User";

    private static final String INSERT_SQL = "INSERT INTO " + TABLE_NAME + " (login, password) VALUES (?, ?)";

    private static final String UPDATE_SQL = "UPDATE " + TABLE_NAME + " SET login = ?, password = ? WHERE id = ?";

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Transactional
    @Override
    public void update(User entity) {
        this.jdbcTemplate.update(UPDATE_SQL,
                entity.getLogin(),
                entity.getPassword(),
                entity.getId());
    }

    @Transactional
    @Override
    public void insert(final User entity) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement prepareStatement = connection.prepareStatement(INSERT_SQL,
                        Statement.RETURN_GENERATED_KEYS);
                prepareStatement.setObject(1, entity.getLogin());
                prepareStatement.setObject(2, entity.getPassword());
                return prepareStatement;
            }
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        entity.setId(id);
    }

    @Override
    protected User createInstanceFromResult(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }
}
