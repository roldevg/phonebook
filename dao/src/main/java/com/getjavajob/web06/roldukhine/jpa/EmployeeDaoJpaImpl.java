package com.getjavajob.web06.roldukhine.jpa;

import com.getjavajob.web06.roldukhine.api.EmployeeDao;
import com.getjavajob.web06.roldukhine.entity.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("employeeDaoJpaImpl")
@Transactional(readOnly = true)
public class EmployeeDaoJpaImpl extends AbstractDaoJpaImpl<Employee> implements EmployeeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void delete(long id) {
        Employee employee = entityManager.find(Employee.class, id);
        if (employee != null) {
            entityManager.remove(employee);
        }
    }

    @Override
    @Transactional
    public void updatePhoto(Employee employee, byte[] photo) {
        employee.setPhoto(photo);
        update(employee);
    }
}
