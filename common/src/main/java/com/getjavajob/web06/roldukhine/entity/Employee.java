package com.getjavajob.web06.roldukhine.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Employee extends BaseEntity {
    private String firstName;
    private String secondName;
    private String lastName;
    private Date birthdate;
    private String email;
    private String icq;
    private String skype;
    private String note;
    private Employee manager;
    private Department department;
    private String workAddress;
    private String homeAddress;
    private List<Phone> phoneList = new ArrayList<>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcq() {
        return icq;
    }

    public void setIcq(String icq) {
        this.icq = icq;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(firstName, employee.firstName) &&
                Objects.equals(secondName, employee.secondName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(birthdate, employee.birthdate) &&
                Objects.equals(email, employee.email) &&
                Objects.equals(icq, employee.icq) &&
                Objects.equals(skype, employee.skype) &&
                Objects.equals(note, employee.note) &&
                Objects.equals(manager, employee.manager) &&
                Objects.equals(department, employee.department) &&
                Objects.equals(workAddress, employee.workAddress) &&
                Objects.equals(homeAddress, employee.homeAddress) &&
                Objects.equals(phoneList, employee.phoneList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName, lastName, birthdate, email, icq, skype, note, manager, department, workAddress, homeAddress, phoneList);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Employee{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", secondName='").append(secondName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", birthdate=").append(birthdate);
        sb.append(", email='").append(email).append('\'');
        sb.append(", icq='").append(icq).append('\'');
        sb.append(", skype='").append(skype).append('\'');
        sb.append(", note='").append(note).append('\'');
        sb.append(", manager=").append(manager);
        sb.append(", department=").append(department != null ? department.getName() : null);
        sb.append(", workAddress='").append(workAddress).append('\'');
        sb.append(", homeAddress='").append(homeAddress).append('\'');
        sb.append(", phoneList=").append(phoneList);
        sb.append('}');
        return sb.toString();
    }
}
