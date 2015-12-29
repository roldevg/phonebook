package com.getjavajob.web06.roldukhine.entity;

public interface IGenericService<T extends BaseEntity> {
    void delete(long id);

    void delete(BaseEntity entity);
}
