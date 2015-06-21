package com.tsystems.javaschool.loginov.logiweb.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Simple java bean that will hold waypoint information.
 */
@Entity
@Table(name = "waypoints", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Waypoint {

    public Waypoint() {}


    private int id;

    private int location_id;

    private int freight_id;

    private String operation;

    private long created_time;

    private long modified_time;


}
