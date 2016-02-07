package com.getjavajob.web06.roldukhine.jpa;

import com.getjavajob.web06.roldukhine.api.CrudDao;
import com.getjavajob.web06.roldukhine.entity.BaseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

@Repository
public abstract class AbstractDaoJpaImpl<T extends BaseEntity> implements CrudDao<T> {

    protected Class<T> entityClass;
    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public AbstractDaoJpaImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) (getClass().getGenericSuperclass());
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @Override
    @Transactional
    public void insert(T entity) {
        entityManager.persist(entity);
        entityManager.flush();
    }

    @Override
    @Transactional
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public T get(long id) {
        @SuppressWarnings("unchecked") T entity = (T) entityManager.find(entityClass, id);
        return entity;
    }

    @Override
    @Transactional
    public List<T> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
        @SuppressWarnings("unchecked") Root<T> from = criteriaQuery.from(entityClass);

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
