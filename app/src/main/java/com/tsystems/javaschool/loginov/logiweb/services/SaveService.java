package com.tsystems.javaschool.loginov.logiweb.services;

import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Adds an item (truck, driver, orders etc.) to the database.
 */
public class SaveService {

    public static final SaveService INSTANCE = new SaveService();

    private SaveService() {
        if (SaveService.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
    }

    public static SaveService getInstance() { return INSTANCE; }

    public Object saveItem(Object item) {
        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        // Save truck and location data
//
//        String city = "Moscow";
//        Location location = new Location(city);
//
//        Query locationQuery = session.createQuery("from Location where city = :city");
//        locationQuery.setString("city", city);
//        Location dbLocation = (Location) locationQuery.uniqueResult();
//
//        if (dbLocation == null) {
//            session.save(location);
//            dbLocation = location;
//        }
//
//        Truck truck = new Truck("ED57102", 2, 500, 1, dbLocation);
//
//        session.save(truck);


        // Save manager data

//        String name = req.getParameter("name");
//        String surname = req.getParameter("surname");
//        String email = req.getParameter("email");
//        int password = Integer.parseInt(req.getParameter("password"));
//
//        Manager manager = new Manager(name, surname, email, password);
//
//        session.save(manager);


        // Save driver data

//        String city1 = "Moscow";
//        Location location1 = new Location(city1);
//        Query locationQuery1 = session.createQuery("from Location where city = :city");
//        locationQuery1.setString("city", city1);
//        Location dbLocation1 = (Location) locationQuery1.uniqueResult();
//        if (dbLocation1 == null) {
//            // in production perhaps you couldn't add new city, show error message
//            session.save(location1);
//            dbLocation1 = location1;
//        }
//
//        String truck_plate_number = "AB12345";
//        Query truckQuery = session.createQuery("from Truck where plate_number = :plate_number");
//        truckQuery.setString("plate_number", truck_plate_number);
//        Truck truck = (Truck) truckQuery.uniqueResult();
//        if (truck == null) {
//            // show message "no truck with the entered plate number, add it first"
//        }
//
//        Driver driver = new Driver("Grisha", "Chichvarkin", "gri@abc.com", 1234, 90, "shift", dbLocation1, truck);
//        // check this driver for existence
//
//        session.save(driver);

//
//        // Save driver status changes data
//        DriverStatusChange driverStatusChange = new DriverStatusChange("free", driver);
//        session.save(driverStatusChange);
//
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
//        Order order = new Order(0, truck, drivers, waypoints);
//        session.save(order);

        session.getTransaction().commit();
        return item;
    }
}
