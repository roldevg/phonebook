package com.getjavajob.web06.roldukhine.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "employee")
public class Employee extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "last_name")
    private String lastName;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "email")
    private String email;

    @Column(name = "icq")
    private String icq;

    @Column(name = "skype")
    private String skype;

    @Column(name = "note")
    private String note;

    @Transient
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "work_address")
    private String workAddress;

    @Column(name = "home_address")
    private String homeAddress;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "employee_to_phone", joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id"))
    private List<Phone> phoneList = new ArrayList<>();

    @Column(name = "photo")
    private byte[] photo;

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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
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
                Objects.equals(phoneList, employee.phoneList) &&
                Arrays.equals(photo, employee.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName, lastName, birthdate, email, icq, skype, note, manager, department, workAddress, homeAddress, phoneList, photo);
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
        sb.append(", department=").append(department);
        sb.append(", workAddress='").append(workAddress).append('\'');
        sb.append(", homeAddress='").append(homeAddress).append('\'');
        sb.append(", phoneList=").append(phoneList);
        sb.append(", photo=").append(Arrays.toString(photo));
        sb.append('}');
        return sb.toString();
    }

    public void addPhoneToPhoneList(Phone phone) {
        phoneList.add(phone);
    }
}
