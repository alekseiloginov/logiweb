package com.tsystems.javaschool.loginov.logiweb.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Simple java bean that will hold driver status change information.
 */
@Entity
@Table(name = "driver_status_changes", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class DriverStatusChange {

    public DriverStatusChange() {}

    public DriverStatusChange(String status, Driver driver) {
        this.status = status;
        this.driver = driver;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "status", nullable = false, length = 255)
    private String status;

    @CreationTimestamp
    @Column(name = "created_time")
    private Date created_time;

    @UpdateTimestamp
    @Column(name = "last_modified_time")
    private Date last_modified_time;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public Date getLast_modified_time() {
        return last_modified_time;
    }

    public void setLast_modified_time(Date last_modified_time) {
        this.last_modified_time = last_modified_time;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
