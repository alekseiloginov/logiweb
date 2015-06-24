package com.tsystems.javaschool.loginov.logiweb.servlets;

import com.tsystems.javaschool.loginov.logiweb.models.Location;
import com.tsystems.javaschool.loginov.logiweb.models.Manager;
import com.tsystems.javaschool.loginov.logiweb.models.Truck;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controller class that handles all client requests (and calls appropriate services).
 */
@WebServlet(name = "MainServlet", urlPatterns = {"/Test"})
public class MainServlet extends HttpServlet {
    Logger logger = Logger.getLogger(MainServlet.class);

    /**
     * Handles POST HTTP methods.
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SessionFactory sessionFactory = (SessionFactory) getServletContext().getAttribute("SessionFactory");
        Session session = sessionFactory.getCurrentSession();


        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        int password = Integer.parseInt(req.getParameter("password"));

        Manager manager = new Manager(name, surname, email, password);

        session.beginTransaction();
        session.save(manager);
        session.getTransaction().commit();



        // Update
//        session = sessionFactory.getCurrentSession();
//        Transaction transaction1 = session.beginTransaction();
//
//        Query query1 = session.createQuery("from Manager where name = :name");
//        query1.setString("name", "Mihail");
//        List managerList1 = query1.list();
//
//        for (Object aManagerList1 : managerList1) {
//            Manager manager1 = (Manager) aManagerList1;
//            manager1.setName("Misha");
//            session.update(manager1);
//        }
//
//        transaction1.commit();


        // Delete
//        session = sessionFactory.getCurrentSession();
//        Transaction transaction2 = session.beginTransaction();
//
//        Query query2 = session.createQuery("from Manager where name = :name");
//        query2.setString("name", "Aleksei");
//        List managerList2 = query2.list();
//
//        for (Object aManagerList2 : managerList2) {
//            Manager manager2 = (Manager) aManagerList2;
//            session.delete(manager2);
//        }
//
//        transaction2.commit();



        session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

//        Criteria criteria = session.createCriteria(Manager.class);
//        criteria.add(Restrictions.eq("email", email))
//                .add(Restrictions.eq("password", password))
//                .setMaxResults(1);
//        Manager checkedManager = (Manager) criteria.uniqueResult();



        Query query = session.createQuery("from Manager");
        List managerList = query.list();

        transaction.commit();


        // Save truck and location data
        session = sessionFactory.getCurrentSession();
        Transaction transaction3 = session.beginTransaction();

        String city = "Moscow";
        Location location = new Location(city);

        Query locationQuery = session.createQuery("from Location where city = :city");
        locationQuery.setString("city", city);
        Location dbLocation = (Location) locationQuery.uniqueResult();

        if (dbLocation == null) {
            session.save(location);
            dbLocation = location;

        }

        Truck truck = new Truck("ED57102", 2, 500, 1, dbLocation);

//        Set<Truck> truckSet = new HashSet<>();
//        truckSet.add(truck);
//
//        dbLocation.setTrucks(truckSet);

        session.save(truck);
        transaction3.commit();




        // Get truck list
        session = sessionFactory.getCurrentSession();
        Transaction transaction0 = session.beginTransaction();

        Query query0 = session.createQuery("from Truck");
        List truckList = query0.list();

        transaction0.commit();


        if (!managerList.isEmpty() && !truckList.isEmpty()) {

            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("managerList", managerList);
            httpSession.setAttribute("truckList", truckList);

            resp.sendRedirect("test.jsp");


        }
    }

    /**
     * Handles GET HTTP methods.
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
