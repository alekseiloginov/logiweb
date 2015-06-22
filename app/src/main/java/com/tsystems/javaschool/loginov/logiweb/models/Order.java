package com.tsystems.javaschool.loginov.logiweb.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

/**
 * Simple java bean that will hold order information.
 */
@Entity
@Table(name = "orders", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Order {

    public Order() {}


    private int id;

    private int completed;

    private int truck_id;

    private Date created_time;

    private Date modified_time;


}
