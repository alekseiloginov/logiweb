package com.tsystems.javaschool.loginov.logiweb.services;

import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Fetches all trucks from the database.
 */
public class TruckListService implements Service {
    static Logger logger = Logger.getLogger(TruckListService.class);

    public String getName() {
        return "TruckListService";
    }

    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        // Criteria style
//        List truckList = session.createCriteria(Truck.class).list();

        Query query;
        session.beginTransaction();

        // Get truck list
        query = session.createQuery("from Truck");
        List truckList = query.list();

        session.getTransaction().commit();

        req.setAttribute("truckList", truckList);

        return "/WEB-INF/jsp/manager/trucks.jsp";
    }
}
