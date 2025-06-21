package com.coding.employee.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "EMP_NAME")
    private String name;
    @Column(name = "EMP_EMAIL",unique = true)
    private String email;
    @Column(name = "DEPARTMENT")
    private String department;
    @Column(name = "SALARY")
    private Double salary;
    @Column(name = "DESIGNATION")
    private String designation;
    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime timeStamp;

    public Employee() {
    }

    public Employee(String name, String email, String department, Double salary, String designation) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.salary = salary;
        this.designation = designation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                ", Designation='" + designation + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
