package com.tsystems.javaschool.loginov.logiweb.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Simple java bean that will hold freight information.
 */
@Entity
@Table(name = "freights", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Freight {

    public Freight() {}


    private int id;

    private String description;

    private int weight;

    private String status;

    private long created_time;

    private long modified_time;


}
