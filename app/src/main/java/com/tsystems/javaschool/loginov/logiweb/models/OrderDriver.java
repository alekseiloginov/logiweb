package com.tsystems.javaschool.loginov.logiweb.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

/**
 * Simple java bean that will hold order driver information.
 */
@Entity
@Table(name = "order_drivers", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class OrderDriver {

    public OrderDriver() {}


    private int order_id;

    private int driver_id;

    private Date created_time;

    private Date modified_time;


}
