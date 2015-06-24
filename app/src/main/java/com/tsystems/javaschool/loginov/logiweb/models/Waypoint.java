package com.tsystems.javaschool.loginov.logiweb.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Simple java bean that will hold waypoint information.
 */
@Entity
@Table(name = "waypoints", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Waypoint {

    public Waypoint() {}

    public Waypoint(String operation, Location location, Freight freight) {
        this.operation = operation;
        this.location = location;
        this.freight = freight;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "operation", nullable = false, length = 255)
    private String operation;

    @CreationTimestamp
    @Column(name = "created_time")
    private Date created_time;

    @UpdateTimestamp
    @Column(name = "last_modified_time")
    private Date last_modified_time;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "freight_id", nullable = false)
    private Freight freight;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Freight getFreight() {
        return freight;
    }

    public void setFreight(Freight freight) {
        this.freight = freight;
    }
}
