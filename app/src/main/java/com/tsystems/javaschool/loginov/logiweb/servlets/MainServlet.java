package com.tsystems.javaschool.loginov.logiweb.servlets;

import com.tsystems.javaschool.loginov.logiweb.controllers.ControllerLocator;
import com.tsystems.javaschool.loginov.logiweb.models.*;
import com.tsystems.javaschool.loginov.logiweb.services.Service;
import com.tsystems.javaschool.loginov.logiweb.services.ServiceLocator;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Controller class that handles all client requests (and calls appropriate services).
 */
@WebServlet(name = "MainServlet", urlPatterns = {"/AuthService", "/RegService", "/LogoutService",
        "/TruckListService", "/DriverListService", "/OrderListService", "/WelcomePageService"})
public class MainServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Logger logger = Logger.getLogger(MainServlet.class);

    /**
     * Handles POST HTTP methods.
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String resultPage;
        String uri = req.getRequestURI();
        logger.info("Requested Resource: " + uri);

        uri = uri.replaceAll("/","");

        Service service = ServiceLocator.getService(uri);
        resultPage = service.execute(req, resp);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(resultPage);
        dispatcher.include(req, resp);



//        SessionFactory sessionFactory = (SessionFactory) getServletContext().getAttribute("SessionFactory");
//        Session session = sessionFactory.getCurrentSession();

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



        // Fetch using criteria
//        session = sessionFactory.getCurrentSession();
//        Transaction transaction = session.beginTransaction();

//        Criteria criteria = session.createCriteria(Manager.class);
//        criteria.add(Restrictions.eq("email", email))
//                .add(Restrictions.eq("password", password))
//                .setMaxResults(1);
//        Manager checkedManager = (Manager) criteria.uniqueResult();

//        transaction.commit();


        // Save truck and location data
//        session = sessionFactory.getCurrentSession();
//        Transaction transaction3 = session.beginTransaction();
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
//        transaction3.commit();



//        session.beginTransaction();

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


        // Fetch data
//        List managerList = session.createCriteria(Manager.class).list();
//        List truckList = session.createCriteria(Truck.class).list();
//        List driverList = session.createCriteria(Driver.class).list();
//        List driverStatusChangeList = session.createCriteria(DriverStatusChange.class).list();
//        List orderList = session.createCriteria(Order.class).list();
//        List waypointList = session.createCriteria(Waypoint.class).list();
//        List freightList = session.createCriteria(Freight.class).list();

//        // Get manager list
//        Query query = session.createQuery("from Manager");
//        List managerList = query.list();
//
//        // Get truck list
//        query = session.createQuery("from Truck");
//        List truckList = query.list();
//
//        // Get driver list
//        query = session.createQuery("from Driver");
//        List driverList = query.list();
//
//        // Get driver status change list
//        query = session.createQuery("from DriverStatusChange");
//        List driverStatusChangeList = query.list();
//
//        // Get order list
//        query = session.createQuery("from Order");
//        List orderList = query.list();
//
//        // Get waypoint list
//        query = session.createQuery("from Waypoint");
//        List waypointList = query.list();
//
//        // Get freight list
//        query = session.createQuery("from Freight");
//        List freightList = query.list();
//
//
//        session.getTransaction().commit();
//
//
//        if (!managerList.isEmpty() && !truckList.isEmpty() && !driverList.isEmpty() && !freightList.isEmpty() && !orderList.isEmpty()) {
//
//            HttpSession httpSession = req.getSession();
//            httpSession.setAttribute("managerList", managerList);
//            httpSession.setAttribute("truckList", truckList);
//            httpSession.setAttribute("driverList", driverList);
//            httpSession.setAttribute("driverStatusChangeList", driverStatusChangeList);
//            httpSession.setAttribute("orderList", orderList);
//            httpSession.setAttribute("waypointList", waypointList);
//            httpSession.setAttribute("freightList", freightList);
//
//            resp.sendRedirect("test.jsp");
//        }
    }

    /**
     * Handles GET HTTP methods.
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String resultPage = "/landing.html";
        String uri = req.getRequestURI().replaceAll("/", "");
        String method = req.getMethod();
        logger.info("Request: " + method + " " + uri);

        if (uri.equals("TruckListService")) {
            Map<String, Object> resultMap = ControllerLocator.execute(uri, method);

            if (resultMap != null) {
                resultPage = (String) resultMap.get("page");
                Object data = resultMap.get("data");
                req.setAttribute("data", data);
            }

        } else {
            Service service = ServiceLocator.getService(uri);
            resultPage = service.execute(req, resp);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(resultPage);
        dispatcher.include(req, resp);
    }

}
