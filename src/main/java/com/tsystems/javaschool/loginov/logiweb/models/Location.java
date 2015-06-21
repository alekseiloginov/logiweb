package com.tsystems.javaschool.loginov.logiweb.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Simple java bean that will hold location information.
 */
@Entity
@Table(name = "locations", uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "city"})})
public class Location {

    public Location() {}


    private int id;

    private String city;

    private long created_time;

    private long modified_time;


}
