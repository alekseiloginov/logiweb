package com.tsystems.javaschool.loginov.logiweb.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Simple java bean that will hold order information.
 */
@Entity
@Table(name = "orders", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Order {

    public Order() {}

    public Order(int completed, Truck truck) {
        this.completed = completed;
        this.truck = truck;
    }

    public Order(int completed, Truck truck, Set<Driver> drivers, Set<Waypoint> waypoints) {
        this.completed = completed;
        this.truck = truck;
        this.drivers = drivers;
        this.waypoints = waypoints;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "completed", nullable = false)
    private int completed;

    @CreationTimestamp
    @Column(name = "created_time")
    private Date created_time;

    @UpdateTimestamp
    @Column(name = "last_modified_time")
    private Date last_modified_time;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "truck_id")
    private Truck truck;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "order_drivers", joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "driver_id"))
    private Set<Driver> drivers;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "order_waypoints", joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "waypoint_id"))
    private Set<Waypoint> waypoints;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
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

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public Set<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(Set<Driver> drivers) {
        this.drivers = drivers;
    }

    public Set<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(Set<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }
}
