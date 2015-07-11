package com.tsystems.javaschool.loginov.logiweb.models;

import org.hibernate.annotations.CreationTimestamp;
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

    public Truck(String plate_number, int driver_number, int capacity, int drivable, Location location) {
        this.plate_number = plate_number;
        this.driver_number = driver_number;
        this.capacity = capacity;
        this.drivable = drivable;
        this.location = location;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @CreationTimestamp
    @Column(name = "created_time")
    private Date created_time;

    @UpdateTimestamp
    @Column(name = "last_modified_time")
    private Date last_modified_time;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "location_id", nullable = false)
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

    public Date getCreated_time() {
        return created_time;
    }

    public Date getLast_modified_time() {
        return last_modified_time;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "plate_number='" + plate_number + '\'' +
                ", driver_number=" + driver_number +
                ", capacity=" + capacity +
                ", drivable=" + drivable +
                ", location=" + location.getCity() +
                '}';
    }
}
