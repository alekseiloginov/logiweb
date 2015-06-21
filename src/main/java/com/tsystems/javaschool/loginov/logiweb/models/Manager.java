package com.tsystems.javaschool.loginov.logiweb.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Simple java bean that will hold manager information.
 */
@Entity
@Table(name = "managers", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Manager {

    public Manager() {}


    public Manager(String name, String surname, String email, int password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "surname", nullable = false, length = 255)
    private String surname;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "password", nullable = false)
    private int password;

//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created_time")
    private Date created_time;

//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "modified_time")
    private Date modified_time;

//    @PrePersist
//    protected void onCreate() {
//        modified_time = created_time = new Date();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        modified_time = new Date();
//    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public Date getModified_time() {
        return modified_time;
    }

    public void setModified_time(Date modified_time) {
        this.modified_time = modified_time;
    }
}
