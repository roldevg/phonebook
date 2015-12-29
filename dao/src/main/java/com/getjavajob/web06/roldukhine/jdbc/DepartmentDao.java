package com.getjavajob.web06.roldukhine.jdbc;

import com.getjavajob.web06.roldukhine.entity.Department;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentDao extends AbstractDao<Department> {

    private static final String TABLE_NAME = "Department";

    private static DepartmentDao ourInstance = new DepartmentDao();

    private DepartmentDao() {
    }

    public static DepartmentDao getInstance() {
        return ourInstance;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getInsertStatement() {
        return "INSERT INTO " + TABLE_NAME + " (name, manager_id) VALUES (?, ?)";
    }

    @Override
    protected String getUpdateByIdStatement() {
        return "UPDATE " + TABLE_NAME + " SET name = ?, manager_id = ? WHERE id = ?";
    }

    @Override
    protected Department createInstanceFromResult(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getLong("id"));
        department.setName(resultSet.getString("name"));
        department.setManager(EmployeeDao.getInstance().get(resultSet.getLong("manager_id")));
        return department;
    }
}
