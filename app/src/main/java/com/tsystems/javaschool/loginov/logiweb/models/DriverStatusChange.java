package com.tsystems.javaschool.loginov.logiweb.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

/**
 * Simple java bean that will hold driver status change information.
 */
@Entity
@Table(name = "driver_status_changes", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class DriverStatusChange {

    public DriverStatusChange() {}


    private int id;

    private int driver_id;

    private String status;

    private Date created_time;

    private Date modified_time;


}
