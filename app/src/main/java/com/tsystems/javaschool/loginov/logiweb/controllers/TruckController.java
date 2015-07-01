package com.tsystems.javaschool.loginov.logiweb.controllers;

import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Homebrew MVC Controller implementation to work with the truck data.
 */
public class TruckController {
    @RequestInfo(value = "TruckListService", method = "GET")
    public static Map<String, Object> getAllTrucks() {
        Map<String, Object> response = new HashMap<>();

        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Query query;
        session.beginTransaction();
        // Get truck list
        query = session.createQuery("from Truck");
        List truckList = query.list();
        session.getTransaction().commit();

        response.put("data", truckList);
        response.put("page", "/WEB-INF/jsp/manager/trucks.jsp");

        return response;
    }
}
