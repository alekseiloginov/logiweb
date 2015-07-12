package com.tsystems.javaschool.loginov.logiweb.services;

import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import com.tsystems.javaschool.loginov.logiweb.exceptions.DuplicateEntryException;
import com.tsystems.javaschool.loginov.logiweb.exceptions.PlateNumberIncorrectException;
import com.tsystems.javaschool.loginov.logiweb.exceptions.PlateNumberNotFoundException;
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
    public Object saveTruck(String plate_number, int driver_number, int capacity, int drivable, String city)
            throws PlateNumberIncorrectException, DuplicateEntryException {

        if (!plate_number.matches("^[a-zA-Z]{2}[0-9]{5}$")) {
            throw new PlateNumberIncorrectException("Plate number should contain 2 letters and 5 digits",
                    "Plate number incorrect");
        }

        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        // Plate number database check for uniqueness
        Query truckCheckQuery = session.createQuery("from Truck where plate_number = :plate_number");
        truckCheckQuery.setString("plate_number", plate_number);
        Truck checkedTruck = (Truck) truckCheckQuery.uniqueResult();
        session.getTransaction().commit();

        if (checkedTruck != null) {
            throw new DuplicateEntryException("Plate number is unique and this one is already present in the database",
                    "Duplicate entry");
        }

        session = sessionFactory.getCurrentSession();
        session.beginTransaction();

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
                             String status, String city, String plate_number)
            throws DuplicateEntryException, PlateNumberNotFoundException {

        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        // Email database check for uniqueness
        Query driverCheckQuery = session.createQuery("from Driver where email = :email");
        driverCheckQuery.setString("email", email);
        Driver checkedDriver = (Driver) driverCheckQuery.uniqueResult();
        session.getTransaction().commit();

        if (checkedDriver != null) {
            throw new DuplicateEntryException("Email is unique and this one is already present in the database",
                    "Duplicate entry");
        }

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
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();

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
            throw new PlateNumberNotFoundException("Entered plate number is not found in the database",
                    "Plate number not found");
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
    public Object saveOrder(String plate_number, int completed) {
        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Query truckQuery = session.createQuery("from Truck where plate_number = :plate_number");
        truckQuery.setString("plate_number", plate_number);
        Truck dbTruck = (Truck) truckQuery.uniqueResult();

        Order order = new Order(completed, dbTruck);

        int savedOrderID = (int) session.save(order);
        logger.info("savedOrderID: " + savedOrderID);

        Query orderQuery = session.createQuery("from Order where id = :savedOrderID");
        orderQuery.setInteger("savedOrderID", savedOrderID);
        Order savedOrder = (Order) orderQuery.uniqueResult();

        session.getTransaction().commit();

        return savedOrder;
    }

    /**
     * Saves a driver to the database and returns saved object.
     */
    public Object saveOrderDriver(int orderID, String driverEmail) {

        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Query orderQuery = session.createQuery("from Order where id = :orderID");
        orderQuery.setInteger("orderID", orderID);
        Order order = (Order) orderQuery.uniqueResult();

        Query driverQuery = session.createQuery("from Driver where email = :driverEmail");
        driverQuery.setString("driverEmail", driverEmail);
        Driver driver = (Driver) driverQuery.uniqueResult();

        // assign an order truck to the driver and update in the database
        driver.setTruck(order.getTruck());
        session.update(driver);

        // assign an driver to the order and update in the database
        Set<Driver> driverSet = order.getDrivers();
        driverSet.add(driver);
        order.setDrivers(driverSet);
        session.update(order);

        session.getTransaction().commit();

        return driver;
    }

    /**
     * Saves a freight with associated waypoints to the database and returns saved freight.
     */
    public Object saveFreight(String name, int weight, String loadingLocation, String unloadingLocation, String status) {
        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Freight freight = new Freight(name, weight, status);
        int savedFreightID = (int) session.save(freight);

        logger.info("savedFreightID: " + savedFreightID);

        Query freightQuery = session.createQuery("from Freight where id = :savedFreightID");
        freightQuery.setInteger("savedFreightID", savedFreightID);
        Freight savedFreight = (Freight) freightQuery.uniqueResult();

        // Now we need to save waypoints associated with the freight's loading and unloading

        // get a location object of the freight loading
        Query locationQuery = session.createQuery("from Location where city = :location");
        locationQuery.setString("location", loadingLocation);
        Location dbLoadingLocation = (Location) locationQuery.uniqueResult();

        // get a location object of the freight unloading
        locationQuery.setString("location", unloadingLocation);
        Location dbUnloadingLocation = (Location) locationQuery.uniqueResult();

        // save associated waypoints
        Waypoint waypoint = new Waypoint("loading", dbLoadingLocation, savedFreight);
        session.save(waypoint);

        waypoint = new Waypoint("unloading", dbUnloadingLocation, savedFreight);
        session.save(waypoint);

        session.getTransaction().commit();

        // add loading and unloading cities to freight object to ease JTable fields parsing
        savedFreight.setLoading(loadingLocation);
        savedFreight.setUnloading(unloadingLocation);

        return savedFreight;
    }

    /**
     * Saves a waypoint to the database and returns saved object.
     */
    public Object saveOrderWaypoint(int orderID, String waypointCity, String waypointFreightName) {

        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Query orderQuery = session.createQuery("from Order where id = :orderID");
        orderQuery.setInteger("orderID", orderID);
        Order order = (Order) orderQuery.uniqueResult();

        // get a location object of the chosen city
        Query locationQuery = session.createQuery("from Location where city = :waypointCity");
        locationQuery.setString("waypointCity", waypointCity);
        Location dbLocation = (Location) locationQuery.uniqueResult();
        int locationID = dbLocation.getId();

        // get a freight object of the chosen freight name
        Query freightQuery = session.createQuery("from Freight where name = :waypointFreightName");
        freightQuery.setString("waypointFreightName", waypointFreightName);
        Freight dbFreight = (Freight) freightQuery.uniqueResult();
        int FreightID = dbFreight.getId();

        Query waypointQuery = session.createQuery("from Waypoint where location_id = :locationID and freight_id = :FreightID");
        waypointQuery.setInteger("locationID", locationID);
        waypointQuery.setInteger("FreightID", FreightID);
        Waypoint waypoint = (Waypoint) waypointQuery.list().get(0);  // may be two similar freight in two similar cities!

        // assign an waypoint to the order and update the order in the database
        Set<Waypoint> waypointSet = order.getWaypoints();
        waypointSet.add(waypoint);
        order.setWaypoints(waypointSet);
        session.update(order);

        session.getTransaction().commit();

        return waypoint;
    }
}
