package com.tsystems.javaschool.loginov.logiweb.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Simple java bean that will hold truck information.
 */
@Entity
@Table(name = "trucks", uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "plate_number"})})
public class Truck {

    public Truck() {}

    public Truck(String plate_number, int driver_number, int capacity, int drivable) {
        this.plate_number = plate_number;
        this.driver_number = driver_number;
        this.capacity = capacity;
        this.drivable = drivable;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "plate_number", nullable = false, length = 7)
    private String plate_number;

    @Column(name = "driver_number", nullable = false)
    private int driver_number;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "drivable", nullable = false)
    private int drivable;

    @GeneratedValue(generator="gen")
    @GenericGenerator(name="gen", strategy="foreign", parameters={@Parameter(name="property", value="location")})
    @Column(name = "location_id", nullable = false)
    private int location_id;

    @CreationTimestamp
    @Column(name = "created_time")
    private Date created_time;

    @UpdateTimestamp
    @Column(name = "modified_time")
    private Date modified_time;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Location location;


    public int getId() {
        return id;
    }

    public String getPlate_number() {
        return plate_number;
    }

    public void setPlate_number(String plate_number) {
        this.plate_number = plate_number;
    }

    public int getDriver_number() {
        return driver_number;
    }

    public void setDriver_number(int driver_number) {
        this.driver_number = driver_number;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getDrivable() {
        return drivable;
    }

    public void setDrivable(int drivable) {
        this.drivable = drivable;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public Date getModified_time() {
        return modified_time;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
