package com.getjavajob.web06.roldukhine.jdbc;

import com.getjavajob.web06.roldukhine.*;
import com.getjavajob.web06.roldukhine.entity.Employee;
import com.getjavajob.web06.roldukhine.entity.Phone;
import com.getjavajob.web06.roldukhine.entity.PhoneType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhoneDao extends AbstractDao<Phone> {
    private static final String TABLE_NAME = "Phone";

    private static final String GET_PHONE_LIST_BY_EMPLOYEE = "SELECT * FROM employee_to_phone WHERE employee_id = ?";
    private static final String INSERT_PHONE_TO_EMPLOYEE = "INSERT INTO employee_to_phone (phone_id, employee_id) VALUES (?, ?)";

    private static PhoneDao ourInstance = new PhoneDao();

    public static PhoneDao getInstance() {
        return ourInstance;
    }

    private PhoneDao() {}

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getInsertStatement() {
        return "INSERT INTO " + TABLE_NAME + " (number, type) VALUES (?, ?)";
    }

    @Override
    protected String getUpdateByIdStatement() {
        return "UPDATE " + TABLE_NAME + " SET number = ?, type = ? WHERE id = ?";
    }

    @Override
    protected Phone createInstanceFromResult(ResultSet resultSet) throws SQLException {
        Phone phone = new Phone();
        phone.setId(resultSet.getLong("id"));
        phone.setNumber(resultSet.getString("number"));
        int rsType = resultSet.getInt("type");
        for (PhoneType type : PhoneType.values()) {
            if (type.ordinal() + 1 == rsType) {
                phone.setType(type);
            }
        }
        return phone;
    }

    public List<Phone> getPhoneListByEmployee(Employee employee) {
        List<Phone> resultList = null;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PHONE_LIST_BY_EMPLOYEE);
            preparedStatement.setObject(1, employee.getId());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                resultList = new ArrayList<>();
                while (rs.next()) {
                    resultList.add(createInstanceFromResult(rs));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionPool.releaseConnection(connection);
        }
        return resultList;
    }

    public void insertPhoneToEmployee(Phone phone, Employee employee) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PHONE_TO_EMPLOYEE);
            preparedStatement.setObject(1, phone.getId());
            preparedStatement.setObject(2, employee.getId());
            preparedStatement.executeUpdate();

            employee.getPhoneList().add(phone);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionPool.releaseConnection(connection);
        }
    }
}
