package com.getjavajob.web06.roldukhine.jdbc;

import com.getjavajob.web06.roldukhine.api.PhoneDao;
import com.getjavajob.web06.roldukhine.entity.Employee;
import com.getjavajob.web06.roldukhine.entity.Phone;
import com.getjavajob.web06.roldukhine.entity.PhoneType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

@Repository
public class PhoneDaoJdbcImpl extends AbstractDaoJdbcImpl<Phone> implements PhoneDao {

    private static final Logger logger = LoggerFactory.getLogger(PhoneDaoJdbcImpl.class);

    private static final String TABLE_NAME = "Phone";

    private static final String INSERT_SQL = "INSERT INTO " + TABLE_NAME + " (number, type) VALUES (?, ?)";

    private static final String UPDATE_SQL = "UPDATE " + TABLE_NAME + " SET number = ?, type = ? WHERE id = ?";

    private static final String GET_PHONE_LIST_BY_EMPLOYEE =
            "SELECT phone.*  \n" +
                    "  FROM EMPLOYEE_TO_PHONE AS ETP" +
                    "      ,PHONE" +
                    " WHERE PHONE.id = ETP.phone_id" +
                    "   AND ETP.employee_id = ?";

    private static final String INSERT_PHONE_TO_EMPLOYEE = "INSERT INTO employee_to_phone (phone_id, employee_id) VALUES (?, ?)";

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Transactional
    @Override
    public void insert(final Phone phone) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement prepareStatement = connection.prepareStatement(INSERT_SQL,
                        Statement.RETURN_GENERATED_KEYS);
                prepareStatement.setObject(1, phone.getNumber());
                prepareStatement.setObject(2, phone.getPhoneTypeTag());
                return prepareStatement;
            }
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        phone.setId(id);
    }

    @Transactional
    @Override
    public void update(Phone phone) {
        this.jdbcTemplate.update(UPDATE_SQL,
                phone.getNumber(),
                phone.getPhoneTypeTag(),
                phone.getId());
    }

    @Override
    protected Phone createInstanceFromResult(ResultSet resultSet) throws SQLException {
        Phone phone = new Phone();
        phone.setId(resultSet.getLong("id"));
        phone.setNumber(resultSet.getString("number"));
        int rsType = resultSet.getInt("type");
        for (PhoneType type : PhoneType.values()) {
            if (type.getTag() == rsType) {
                phone.setType(type);
            }
        }
        return phone;
    }

    public List<Phone> getPhoneListByEmployee(Employee employee) {
        final long employeeId = employee.getId();

        List<Phone> phoneList = this.jdbcTemplate.query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(GET_PHONE_LIST_BY_EMPLOYEE);
                preparedStatement.setObject(1, employeeId);
                return preparedStatement;
            }
        }, new RowMapper<Phone>() {
            @Override
            public Phone mapRow(ResultSet resultSet, int i) throws SQLException {
                return createInstanceFromResult(resultSet);
            }
        });

        return phoneList;
    }

    @Transactional
    public void insertPhoneToEmployee(Phone phone, Employee employee) {
        this.jdbcTemplate.update(INSERT_PHONE_TO_EMPLOYEE,
                phone.getId(),
                employee.getId());
    }
}
