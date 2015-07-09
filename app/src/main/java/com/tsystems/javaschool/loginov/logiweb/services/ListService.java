package com.tsystems.javaschool.loginov.logiweb.services;

import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import com.tsystems.javaschool.loginov.logiweb.models.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Set;

/**
 * Fetches all items (trucks, drivers, orders etc.) from the database.
 */
public class ListService {

    public static final ListService INSTANCE = new ListService();

    private ListService() {
        if (ListService.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
    }

    public static ListService getInstance() { return INSTANCE; }

    /**
     * Gets all items provided with the string from the database and returns them as a list.
     */
    public List getAllItems(String item, String sorting) {
        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        // Criteria style
//        List truckList = session.createCriteria(Truck.class).list();

        // HQL style
        List itemList;
        if (sorting != null) {
            itemList = session.createQuery("from " + item + " order by " + sorting).list();
        } else {
            itemList = session.createQuery("from " + item).list();
        }

        // dirty hack
        if (item.equals("Freight")) {

            // We need to get cities associated with the freight's loading and unloading from the Waypoint objects

            for (Object freightObject : itemList) {
                Freight freight = (Freight) freightObject;
                int freightID = freight.getId();

                // get a waypoint object of the freight loading
                Query waypointQuery =
                        session.createQuery("from Waypoint where freight_id = :freight_id and operation = :operation");
                waypointQuery.setInteger("freight_id", freightID);
                waypointQuery.setString("operation", "loading");
                Waypoint dbLoadingWaypoint = (Waypoint) waypointQuery.uniqueResult();

                // get a waypoint object of the freight unloading
                waypointQuery.setString("operation", "unloading");
                Waypoint dbLUnloadingWaypoint = (Waypoint) waypointQuery.uniqueResult();

                String loadingLocation = dbLoadingWaypoint.getLocation().getCity();
                String unloadingLocation = dbLUnloadingWaypoint.getLocation().getCity();

                freight.setLoading(loadingLocation);
                freight.setUnloading(unloadingLocation);
            }
        }

        session.getTransaction().commit();
        return itemList;
    }

    /**
     * Gets all drivers for the provided order ID from the database and returns them as a set of drivers.
     */
    public Set<Driver> getAllOrderDrivers(int orderID) {
        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Query orderQuery = session.createQuery("from Order where id = :orderID");
        orderQuery.setInteger("orderID", orderID);
        Order order = (Order) orderQuery.uniqueResult();
        session.getTransaction().commit();

        return order.getDrivers();
    }

    /**
     * Gets all waypoints for the provided order ID from the database and returns them as a set of waypoints.
     */
    public Set<Waypoint> getAllOrderWaypoints(int orderID) {
        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Query orderQuery = session.createQuery("from Order where id = :orderID");
        orderQuery.setInteger("orderID", orderID);
        Order order = (Order) orderQuery.uniqueResult();
        session.getTransaction().commit();

        return order.getWaypoints();
    }
}
