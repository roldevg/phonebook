package com.getjavajob.web06.roldukhine.jdbc;

import com.getjavajob.web06.roldukhine.api.DepartmentDao;
import com.getjavajob.web06.roldukhine.api.EmployeeDao;
import com.getjavajob.web06.roldukhine.api.PhoneDao;
import com.getjavajob.web06.roldukhine.entity.BaseEntity;
import com.getjavajob.web06.roldukhine.entity.Department;
import com.getjavajob.web06.roldukhine.entity.Employee;
import com.getjavajob.web06.roldukhine.entity.Phone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

@Repository
public class EmployeeDaoJdbcImpl extends AbstractDaoJdbcImpl<Employee> implements EmployeeDao {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeDaoJdbcImpl.class);

    private static final String TABLE_NAME = "Employee";
    private static final String INSERT_SQL = "INSERT INTO " + TABLE_NAME + " (first_name, second_name, last_name, birthdate, email, icq, " +
            "skype, note, manager_id, department_id, work_address, home_address, photo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE " + TABLE_NAME + " SET first_name = ?, second_name = ?, last_name = ?, birthdate = ?, email = ?, icq = ?, " +
            "skype = ?, note = ?, manager_id = ?, department_id = ?, work_address = ?, home_address = ?, photo = ? WHERE id = ?";
    private static final String UPDATE_PHOTO = "UPDATE " + TABLE_NAME + " SET PHOTO = ? WHERE id = ?";
    @Autowired
    private PhoneDao phoneDao;
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public String getTableName() {
        logger.trace("getTableName");
        logger.debug("return TableName: " + TABLE_NAME);
        return TABLE_NAME;
    }

    @Transactional
    @Override
    public void insert(final Employee employee) {
        logger.debug("insert with employee {}", employee);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement prepareStatement = connection.prepareStatement(INSERT_SQL,
                        Statement.RETURN_GENERATED_KEYS);
                prepareStatement.setObject(1, employee.getFirstName());
                prepareStatement.setObject(2, employee.getSecondName());
                prepareStatement.setObject(3, employee.getLastName());
                prepareStatement.setObject(4, employee.getBirthdate());
                prepareStatement.setObject(5, employee.getEmail());
                prepareStatement.setObject(6, employee.getIcq());
                prepareStatement.setObject(7, employee.getSkype());
                prepareStatement.setObject(8, employee.getNote());
                prepareStatement.setObject(9, employee.getManager() != null ? employee.getManager().getId() : null);
                prepareStatement.setObject(10, employee.getDepartment() != null ? employee.getDepartment().getId() : null);
                prepareStatement.setObject(11, employee.getWorkAddress());
                prepareStatement.setObject(12, employee.getHomeAddress());
                prepareStatement.setObject(13, employee.getPhoto());
                return prepareStatement;
            }
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        logger.debug("return new id {}", id);
        employee.setId(id);
    }

    @Transactional
    @Override
    public void update(Employee employee) {
        logger.debug("update with employee {}", employee);
        this.jdbcTemplate.update(UPDATE_SQL,
                employee.getFirstName(),
                employee.getSecondName(),
                employee.getLastName(),
                employee.getBirthdate(),
                employee.getEmail(),
                employee.getIcq(),
                employee.getSkype(),
                employee.getNote(),
                employee.getManager() != null ? employee.getManager().getId() : null,
                employee.getDepartment() != null ? employee.getDepartment().getId() : null,
                employee.getWorkAddress(),
                employee.getHomeAddress(),
                employee.getPhoto(),
                employee.getId());

        final List<Phone> phoneList = employee.getPhoneList();
        for (Phone phone : phoneList) {
            if (phone.getId() == 0) {
                logger.debug("insert new phone {}", phone);
                phoneDao.insert(phone);
                phoneDao.insertPhoneToEmployee(phone, employee);
            } else {
                logger.debug("update phone {}", phone);
                phoneDao.update(phone);
            }
        }
    }

    @Transactional
    @Override
    public void updatePhoto(final Employee employee, final byte[] photo) {
        logger.debug("update photo");
        LobHandler lobHandler = new DefaultLobHandler();
        this.jdbcTemplate.execute(UPDATE_PHOTO,
                new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
                    protected void setValues(PreparedStatement ps, LobCreator lobCreator)
                            throws SQLException {
                        lobCreator.setBlobAsBytes(ps, 1, photo);
                        ps.setLong(2, employee.getId());
                    }
                }
        );

        employee.setPhoto(photo);
    }

    @Override
    protected Employee createInstanceFromResult(ResultSet resultSet) throws SQLException {
        logger.debug("createInstanceFromResult: resultSet {}", resultSet);
        Department department = null;
        long department_id = resultSet.getLong("department_id");
        if (department_id != BaseEntity.NO_EXIST_ID_ENTITY) {
            department = departmentDao.get(department_id);
        }

        Employee manager = null;
        long manager_id = resultSet.getLong("manager_id");
        if (manager_id != BaseEntity.NO_EXIST_ID_ENTITY) {
            manager = get(manager_id);
        }

        Employee employee = new Employee();
        employee.setId(resultSet.getLong("id"));
        employee.setFirstName(resultSet.getString("first_name"));
        employee.setSecondName(resultSet.getString("second_name"));
        employee.setLastName(resultSet.getString("last_name"));
        employee.setBirthdate(resultSet.getDate("birthdate"));
        employee.setEmail(resultSet.getString("email"));
        employee.setIcq(resultSet.getString("icq"));
        employee.setSkype(resultSet.getString("skype"));
        employee.setNote(resultSet.getString("note"));
        employee.setWorkAddress(resultSet.getString("work_address"));
        employee.setHomeAddress(resultSet.getString("home_address"));
        employee.setManager(manager);
        employee.setDepartment(department);

        Blob photo = resultSet.getBlob("photo");
        if (photo != null) {
            byte[] photoArray = photo.getBytes(1, (int) photo.length());
            employee.setPhoto(photoArray);
        }

        List<Phone> phoneListByEmployee = phoneDao.getPhoneListByEmployee(employee);
        employee.setPhoneList(phoneListByEmployee);

        return employee;
    }
}
