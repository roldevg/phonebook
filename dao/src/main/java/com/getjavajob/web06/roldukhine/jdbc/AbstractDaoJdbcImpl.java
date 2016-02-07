package com.getjavajob.web06.roldukhine.jdbc;

import com.getjavajob.web06.roldukhine.api.CrudDao;
import com.getjavajob.web06.roldukhine.entity.BaseEntity;
import com.getjavajob.web06.roldukhine.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("abstractDao")
public abstract class AbstractDaoJdbcImpl<T extends BaseEntity> implements CrudDao<T> {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    protected abstract String getTableName();

    public abstract void update(T entity);

    public abstract void insert(T entity);

    protected abstract T createInstanceFromResult(ResultSet resultSet) throws SQLException;

    protected String getSelectAllStatement() {
        return "SELECT * FROM " + getTableName();
    }

    protected String getSelectByIdStatement() {
        return getSelectAllStatement() + " WHERE id = ?";
    }

    protected String getDeleteByIdStatement() {
        return "DELETE FROM " + getTableName() + " WHERE id = ?";
    }

    public Connection getConnectionFromDataSource() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T get(long id) {
        try (Connection connection = getConnectionFromDataSource()) {
            try (PreparedStatement prepareStatement = connection.prepareStatement(getSelectByIdStatement())) {
                prepareStatement.setLong(1, id);
                try (ResultSet resultSet = prepareStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return createInstanceFromResult(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return null;
    }

    @Override
    public void delete(T entity) {
        long id = entity.getId();
        delete(id);
    }

    public void delete(long id) {
        this.jdbcTemplate.update(getDeleteByIdStatement(), id);
    }

    @Override
    public List<T> getAll() {
        return this.jdbcTemplate.query(getSelectAllStatement(), new RowMapper<T>() {
            @Override
            public T mapRow(ResultSet resultSet, int i) throws SQLException {
                return createInstanceFromResult(resultSet);
            }
        });
    }
}
