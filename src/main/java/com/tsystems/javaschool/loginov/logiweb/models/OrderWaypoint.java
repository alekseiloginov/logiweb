package com.tsystems.javaschool.loginov.logiweb.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Simple java bean that will hold order waypoint information.
 */
@Entity
@Table(name = "order_waypoints", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class OrderWaypoint {

    public OrderWaypoint() {}


    private int order_id;

    private int waypoint_id;

    private long created_time;

    private long modified_time;


}
