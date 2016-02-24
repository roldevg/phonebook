package com.getjavajob.web06.roldukhine.jpa;

import com.getjavajob.web06.roldukhine.api.CrudDao;
import com.getjavajob.web06.roldukhine.entity.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Repository
public abstract class AbstractDaoJpaImpl<T extends BaseEntity> implements CrudDao<T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractDaoJpaImpl.class);

    protected Class<T> entityClass;

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public AbstractDaoJpaImpl() {
        logger.trace("construct AbstractDaoJpaImpl");
        ParameterizedType genericSuperclass = (ParameterizedType) (getClass().getGenericSuperclass());
        logger.debug("genericSuperclass = {}", genericSuperclass);
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @Override
    public void insert(T entity) {
        logger.trace("insert {}", entity);
        entityManager.persist(entity);
        entityManager.flush();
    }

    @Override
    public void update(T entity) {
        logger.trace("update {}", entity);
        entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {
        logger.trace("delete {}", entity);
        entityManager.remove(entity);
    }

    @Override
    public T get(long id) {
        logger.trace("get with input id {}", id);
        @SuppressWarnings("unchecked") T entity = (T) entityManager.find(entityClass, id);
        return entity;
    }

    @Override
    public List<T> getAll() {
        logger.trace("getAll");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        logger.debug("getCriteriaBuilder {}", criteriaBuilder);
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        logger.debug("criteriaQuery {} ", criteriaQuery);
        Root<T> from = criteriaQuery.from(entityClass);
        CriteriaQuery<T> select = criteriaQuery.select(from);
        logger.debug("CriteriaQuery select {} ", select);
        return entityManager.createQuery(select).getResultList();
    }
}
