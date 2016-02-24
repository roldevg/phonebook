package com.getjavajob.web06.roldukhine.jpa;

import com.getjavajob.web06.roldukhine.api.EmployeeDao;
import com.getjavajob.web06.roldukhine.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("employeeDaoJpaImpl")
public class EmployeeDaoJpaImpl extends AbstractDaoJpaImpl<Employee> implements EmployeeDao {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeDaoJpaImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void delete(long id) {
        logger.trace("delete with id {}", id);
        Employee employee = entityManager.find(Employee.class, id);
        if (employee != null) {
            entityManager.remove(employee);
        }
    }

    @Override
    public void updatePhoto(Employee employee, byte[] photo) {
        logger.trace("updatePhoto employee {}, photo {}", employee, photo);
        employee.setPhoto(photo);
        update(employee);
    }
}
