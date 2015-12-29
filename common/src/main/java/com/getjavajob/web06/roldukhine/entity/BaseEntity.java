package com.getjavajob.web06.roldukhine.entity;

public abstract class BaseEntity {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean idEqual(BaseEntity entity) {
        return entity.getId() == id;
    }
}
