package com.tsystems.javaschool.loginov.logiweb.services;

import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import com.tsystems.javaschool.loginov.logiweb.models.*;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

/**
 * Adds an item (truck, driver, order etc.) to the database.
 */
public class SaveService {

    // Save driver status changes data for the future saveDriverStatusChange() method
//        DriverStatusChange driverStatusChange = new DriverStatusChange("free", driver);
//        session.save(driverStatusChange);

    static Logger logger = Logger.getLogger(SaveService.class);

    public static final SaveService INSTANCE = new SaveService();

    private SaveService() {
        if (SaveService.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
    }

    public static SaveService getInstance() { return INSTANCE; }

    /**
     * Saves a truck to the database and returns saved object.
     */
    public Object saveTruck(String plate_number, int driver_number, int capacity, int drivable, String city) {
        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        // TODO add plate_number check

        Query locationQuery = session.createQuery("from Location where city = :city");
        locationQuery.setString("city", city);
        Location dbLocation = (Location) locationQuery.uniqueResult();

        if (dbLocation == null) {
            Location location = new Location(city);
            session.save(location);
            dbLocation = location;
        }

        Truck truck = new Truck(plate_number, driver_number, capacity, drivable, dbLocation);
        int savedTruckID = (int) session.save(truck);

        logger.info("savedTruckID: " + savedTruckID);

        Query truckQuery = session.createQuery("from Truck where id = :savedTruckID");
        truckQuery.setInteger("savedTruckID", savedTruckID);
        Truck savedTruck = (Truck) truckQuery.uniqueResult();

        session.getTransaction().commit();

        return savedTruck;
    }

    /**
     * Saves a driver to the database and returns saved object.
     */
    public Object saveDriver(String name, String surname, String email, String password, int worked_hours,
                             String status, String city, String plate_number) {

        // Password encryption using MD5
        String encryptedPassword = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            byte[] bytes = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();

            for (byte aByte : bytes) {
                stringBuilder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            encryptedPassword = stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // DB save
        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        // TODO add email check

        Query locationQuery = session.createQuery("from Location where city = :city");
        locationQuery.setString("city", city);
        Location dbLocation = (Location) locationQuery.uniqueResult();

        if (dbLocation == null) {
            // in production perhaps you couldn't add new city, show error message
            Location location = new Location(city);
            session.save(location);
            dbLocation = location;
        }

        Query truckQuery = session.createQuery("from Truck where plate_number = :plate_number");
        truckQuery.setString("plate_number", plate_number);
        Truck dbTruck = (Truck) truckQuery.uniqueResult();

        if (dbTruck == null) {
            // show message "no truck with the entered plate number, add it first"
        }

        Driver driver = new Driver(name, surname, email, encryptedPassword, worked_hours, status, dbLocation, dbTruck);

        int savedDriverID = (int) session.save(driver);
        logger.info("savedDriverID: " + savedDriverID);

        Query driverQuery = session.createQuery("from Driver where id = :savedDriverID");
        driverQuery.setInteger("savedDriverID", savedDriverID);
        Driver savedDriver = (Driver) driverQuery.uniqueResult();

        session.getTransaction().commit();

        return savedDriver;
    }

    /**
     * Saves an order to the database and returns saved object.
     */
    public Object saveOrder(int completed, String plate_number, Set<Driver> drivers, Set<Waypoint> waypoints) {
        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        // TODO add checks: 1. all freight should be loaded and unloaded, 2. valid trucks, 3. valid drives

        Query truckQuery = session.createQuery("from Truck where plate_number = :plate_number");
        truckQuery.setString("plate_number", plate_number);
        Truck dbTruck = (Truck) truckQuery.uniqueResult();

        if (dbTruck == null) {
            // show message "no truck with the entered plate number, add it first"
        }

//        // Save freight data

//        Freight freight = new Freight("iphones", 500, "shipped");
//        session.save(freight);
//

//        // Save waypoint data

//        Waypoint waypoint = new Waypoint("unloading", dbLocation1, freight);
//        session.save(waypoint);

//
//        // Save order data
//
//        Set<Driver> drivers = new HashSet<>();
//        drivers.add(driver);
//        drivers.add(new Driver("Nasty", "Molodaia", "nasty@abc.com", 1234, 70, "driving", dbLocation1, truck));
//
//        Set<Waypoint> waypoints = new HashSet<>();
//        waypoints.add(waypoint);
//        waypoints.add(new Waypoint("loading", dbLocation1, new Freight("galaxies", 400, "delivered")));
//
        Order order = new Order(completed, dbTruck, drivers, waypoints);

        int savedOrderID = (int) session.save(order);
        logger.info("savedOrderID: " + savedOrderID);

        Query orderQuery = session.createQuery("from Order where id = :savedOrderID");
        orderQuery.setInteger("savedOrderID", savedOrderID);
        Order savedOrder = (Order) orderQuery.uniqueResult();

        session.getTransaction().commit();

        return savedOrder;
    }
}
