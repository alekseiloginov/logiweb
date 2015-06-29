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
 * Fetches all drivers from the database.
 */
public class DriverListService implements Service {
    static Logger logger = Logger.getLogger(DriverListService.class);

    public String getName() {
        return "DriverListService";
    }

    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        // Criteria style
//        List driverList = session.createCriteria(Driver.class).list();

        Query query;
        session.beginTransaction();

        // Get driver list
        query = session.createQuery("from Driver");
        List driverList = query.list();

        session.getTransaction().commit();

        req.setAttribute("driverList", driverList);

        return "/WEB-INF/jsp/manager/drivers.jsp";
    }
}
