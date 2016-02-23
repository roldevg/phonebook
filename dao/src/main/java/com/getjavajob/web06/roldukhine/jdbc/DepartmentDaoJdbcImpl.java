package com.getjavajob.web06.roldukhine.jdbc;

import com.getjavajob.web06.roldukhine.api.DepartmentDao;
import com.getjavajob.web06.roldukhine.api.EmployeeDao;
import com.getjavajob.web06.roldukhine.entity.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;

@Repository
public class DepartmentDaoJdbcImpl extends AbstractDaoJdbcImpl<Department> implements DepartmentDao {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentDaoJdbcImpl.class);

    private static final String TABLE_NAME = "Department";
    private static final String UPDATE_SQL = "UPDATE " + TABLE_NAME + " SET name = ?, manager_id = ? WHERE id = ?";
    private static final String INSERT_SQL = "INSERT INTO " + TABLE_NAME + " (name, manager_id) VALUES (?, ?)";

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Transactional
    @Override
    public void insert(final Department department) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement prepareStatement = connection.prepareStatement(INSERT_SQL,
                        Statement.RETURN_GENERATED_KEYS);
                prepareStatement.setObject(1, department.getName());
                prepareStatement.setObject(2, department.getManager() != null ? department.getManager().getId() : null);
                return prepareStatement;
            }
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        department.setId(id);
    }

    @Transactional
    @Override
    public void update(Department department) {
        this.jdbcTemplate.update(UPDATE_SQL,
                department.getName(),
                department.getManager() != null ? department.getManager().getId() : null);
    }

    @Override
    protected Department createInstanceFromResult(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getLong("id"));
        department.setName(resultSet.getString("name"));

        long manager_id = resultSet.getLong("manager_id");
        if (manager_id != 0) {
            department.setManager(employeeDao.get(manager_id));
        }

        return department;
    }
}
