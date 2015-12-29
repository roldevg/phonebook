package com.getjavajob.web06.roldukhine.jdbc;

import com.getjavajob.web06.roldukhine.entity.Department;
import com.getjavajob.web06.roldukhine.entity.Employee;
import com.getjavajob.web06.roldukhine.entity.Phone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeDao extends AbstractDao<Employee> {

    private static final String TABLE_NAME = "Employee";

    private static final EmployeeDao instance = new EmployeeDao();

    private EmployeeDao() {
    }

    public static EmployeeDao getInstance() {
        return instance;
    }

    @Override
    protected String getInsertStatement() {
        return "INSERT INTO " + TABLE_NAME + " (first_name, second_name, last_name, birthdate, email, icq, " +
                "skype, note, work_address, home_address, manager_id, department_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateByIdStatement() {
        return "UPDATE " + TABLE_NAME + " SET first_name = ?, second_name = ?, last_name = ?, birthdate = ?, " +
                "email = ?, icq = ?, skype = ?, note = ?, work_address = ?, home_address = ?, manager_id = ?, department_id = ? WHERE id = ?";
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Employee createInstanceFromResult(ResultSet resultSet) throws SQLException {

        Department department = DepartmentDao.getInstance().get(resultSet.getLong("department_id"));
        Employee manager = EmployeeDao.getInstance().get(resultSet.getLong("manager_id"));

        Employee employee = new Employee();
        employee.setId(resultSet.getLong("id"));
        employee.setFirstName(resultSet.getString("first_name"));
        employee.setSecondName(resultSet.getString("second_name"));
        employee.setLastName(resultSet.getString("last_name"));
        employee.setBirthdate(resultSet.getDate("birthdate"));
        employee.setEmail(resultSet.getString("email"));
        employee.setIcq(resultSet.getString("icq"));
        employee.setNote(resultSet.getString("note"));
        employee.setWorkAddress(resultSet.getString("work_address"));
        employee.setHomeAddress(resultSet.getString("home_address"));
        employee.setManager(manager);
        employee.setDepartment(department);

        PhoneDao phoneDao = PhoneDao.getInstance();
        List<Phone> phoneList = phoneDao.getPhoneListByEmployee(employee);
        employee.setPhoneList(phoneList);
        return employee;
    }
}
