package com.tsystems.javaschool.loginov.logiweb.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Simple java bean that will hold truck information.
 */
@Entity
@Table(name = "trucks", uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "plate_number"})})
public class Truck {

    public Truck() {}


    private int id;

    private String plate_number;

    private int driver_number;

    private int capacity;

    private int drivable;

    private int location_id;

    private long created_time;

    private long modified_time;


}
