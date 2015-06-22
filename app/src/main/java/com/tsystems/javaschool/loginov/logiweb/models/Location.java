package com.tsystems.javaschool.loginov.logiweb.models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Simple java bean that will hold location information.
 */
@Entity
@Table(name = "locations", uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "city"})})
public class Location {

    public Location() {}

    public Location(String city) {
        this.city = city;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "city", nullable = false, length = 255)
    private String city;

    @CreationTimestamp
    @Column(name = "created_time")
    private Date created_time;

    @UpdateTimestamp
    @Column(name = "modified_time")
    private Date modified_time;


    @OneToOne(mappedBy = "location", cascade = CascadeType.ALL)
    private Truck truck;


    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public Date getModified_time() {
        return modified_time;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }
}
