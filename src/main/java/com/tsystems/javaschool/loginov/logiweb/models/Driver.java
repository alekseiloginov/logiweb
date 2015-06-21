package com.tsystems.javaschool.loginov.logiweb.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Simple java bean that will hold driver information.
 */
@Entity
@Table(name = "driver", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Driver {

    public Driver() {}


    private int id;

    private String plate_number;

    private int driver_number;

    private int capacity;

    private int drivable;

    private int location_id;

    private long created_time;

    private long modified_time;




}
