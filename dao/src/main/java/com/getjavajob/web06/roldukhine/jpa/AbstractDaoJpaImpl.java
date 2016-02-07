package com.getjavajob.web06.roldukhine.jpa;

import com.getjavajob.web06.roldukhine.api.CrudDao;
import com.getjavajob.web06.roldukhine.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
public class AbstractDaoJpaImpl<T extends BaseEntity> implements CrudDao<T> {

    private Class T;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public AbstractDaoJpaImpl(Class t) {
        T = t;
    }

    @Override
    @Transactional
    public void insert(T entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public void update(T entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.find(T, entity.getId());
        entityManager.remove(entity);
    }

    @Override
    public T get(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        @SuppressWarnings("unchecked") T entity = (T) entityManager.find(T, id);
        return entity;
    }

    @Override
    public List<T> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
        @SuppressWarnings("unchecked") Root<T> from = criteriaQuery.from(T);

        CriteriaQuery<Object> select = criteriaQuery.select(from);
        TypedQuery<Object> typedQuery = entityManager.createQuery(select);
        List<Object> resultList = typedQuery.getResultList();

        List<T> allListEntity = new ArrayList<>();
        for (Object obj : resultList) {
            @SuppressWarnings("unchecked") T employee = (T) obj;
            allListEntity.add(employee);
        }

        return allListEntity;
    }
}
