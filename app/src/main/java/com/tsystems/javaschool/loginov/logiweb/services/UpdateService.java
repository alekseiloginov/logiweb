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
 * Updates an item (truck, driver, order etc.) in the database.
 */
public class UpdateService {
    static Logger logger = Logger.getLogger(UpdateService.class);

    public static final UpdateService INSTANCE = new UpdateService();

    private UpdateService() {
        if (UpdateService.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
    }

    public static UpdateService getInstance() { return INSTANCE; }

    /**
     * Updates a truck in the database.
     */
    public void updateTruck(int id, String plate_number, int driver_number, int capacity, int drivable, String city) {

        logger.info("ID of the truck to update: " + id);

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

        Query truckQuery = session.createQuery("from Truck where id = :id");
        truckQuery.setInteger("id", id);
        Truck truckToUpdate = (Truck) truckQuery.uniqueResult();
        logger.info("Truck to update: " + truckToUpdate);

        truckToUpdate.setPlate_number(plate_number);
        truckToUpdate.setDriver_number(driver_number);
        truckToUpdate.setCapacity(capacity);
        truckToUpdate.setDrivable(drivable);
        truckToUpdate.setLocation(dbLocation);

        session.update(truckToUpdate);
        logger.info("Updated truck: " + truckToUpdate);

        session.getTransaction().commit();
    }


    /**
     * Updates a driver in the database.
     */
    public void updateDriver(int id, String name, String surname, String email, String password, int worked_hours,
                             String status, String city, String plate_number) {

        logger.info("ID of the driver to update: " + id);

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

        // DB update
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

        Query driverQuery = session.createQuery("from Driver where id = :id");
        driverQuery.setInteger("id", id);
        Driver driverToUpdate = (Driver) driverQuery.uniqueResult();
        logger.info("Driver to update: " + driverToUpdate);

        driverToUpdate.setName(name);
        driverToUpdate.setSurname(surname);
        driverToUpdate.setEmail(email);
        driverToUpdate.setPassword(encryptedPassword);
        driverToUpdate.setWorked_hours(worked_hours);
        driverToUpdate.setStatus(status);
        driverToUpdate.setLocation(dbLocation);
        driverToUpdate.setTruck(dbTruck);

        session.update(driverToUpdate);
        logger.info("Updated driver: " + driverToUpdate);

        session.getTransaction().commit();
    }

    /**
     * Updates an order in the database.
     */
    public void updateOrder(int id, int completed, String plate_number, Set<Driver> drivers, Set<Waypoint> waypoints) {

        logger.info("ID of the order to update: " + id);

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


        Query orderQuery = session.createQuery("from Order where id = :id");
        orderQuery.setInteger("id", id);
        Order orderToUpdate = (Order) orderQuery.uniqueResult();
        logger.info("Order to update: " + orderToUpdate);

        orderToUpdate.setCompleted(completed);
        orderToUpdate.setTruck(dbTruck);
        orderToUpdate.setDrivers(drivers);
        orderToUpdate.setWaypoints(waypoints);

        session.update(orderToUpdate);
        logger.info("Updated order: " + orderToUpdate);
    }
}
