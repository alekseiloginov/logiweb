package com.tsystems.javaschool.loginov.logiweb.services;

import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import com.tsystems.javaschool.loginov.logiweb.models.Truck;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Fetches all trucks from the database.
 */
public class TruckListService {

    public static final TruckListService INSTANCE = new TruckListService();

    private TruckListService() {
        if (TruckListService.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
    }

    public static TruckListService getInstance() { return INSTANCE; }

    public List getAllTrucks() {
        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        // Criteria style
        List truckList = session.createCriteria(Truck.class).list();

        // HQL style
//        List truckList = session.createQuery("from Truck").list();

        session.getTransaction().commit();
        return truckList;
    }
}
