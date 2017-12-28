package com.getjavajob.roldukhine.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "department")
public class Department extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Department that = (Department) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(manager, that.manager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, manager);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Department{");
        sb.append("name='").append(name).append('\'');
        sb.append(", manager=").append(manager);
        sb.append('}');
        return sb.toString();
    }
}
