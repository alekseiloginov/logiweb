package com.tsystems.javaschool.loginov.logiweb.services;

import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

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
    public List getAllItems(String item) {
        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        // Criteria style
//        List truckList = session.createCriteria(Truck.class).list();

        // HQL style
        List truckList = session.createQuery("from " + item).list();

        session.getTransaction().commit();
        return truckList;
    }
}
