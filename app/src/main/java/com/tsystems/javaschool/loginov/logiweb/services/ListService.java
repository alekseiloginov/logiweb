package com.tsystems.javaschool.loginov.logiweb.services;

import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import com.tsystems.javaschool.loginov.logiweb.models.Driver;
import com.tsystems.javaschool.loginov.logiweb.models.Order;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Set;

/**
 * Fetches all items (trucks, drivers, orders etc.) from the database.
 */
public class ListService {
    static Logger logger = Logger.getLogger(ListService.class);

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

        session.getTransaction().commit();
        return itemList;
    }

    /**
     * Gets all drivers for the provided order ID from the database and returns them as a set of drivers.
     */
    public Set<Driver> getAllOrderDrivers(int orderID, String sorting) {
        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Query orderQuery;

        // Do we need SORTING here?

        if (sorting != null) {
            orderQuery = session.createQuery("from Order where id = :orderID order by " + sorting);
        } else {
            orderQuery = session.createQuery("from Order where id = :orderID");
        }
        orderQuery.setInteger("orderID", orderID);
        Order order = (Order) orderQuery.uniqueResult();
        session.getTransaction().commit();

        Set<Driver> driverSet = order.getDrivers();

        return driverSet;
    }
}
