package com.getjavajob.web06.roldukhine.api;

import com.getjavajob.web06.roldukhine.entity.BaseEntity;

import java.util.List;

public interface CrudDao<T extends BaseEntity> {
    void insert(T entity);

    void update(T entity);

    void delete(T entity);

    T get(long id);

    List<T> getAll();
}
