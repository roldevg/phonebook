package com.roldukhine.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "phone")
public class Phone extends BaseEntity {

    @Column(name = "number")
    private String number;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private PhoneType type = PhoneType.WORK;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }

    public int getPhoneTypeTag() {
        return type.getTag();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Phone phone = (Phone) o;
        return Objects.equals(number, phone.number) &&
                type == phone.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, type);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Phone{");
        sb.append("number='").append(number).append('\'');
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
