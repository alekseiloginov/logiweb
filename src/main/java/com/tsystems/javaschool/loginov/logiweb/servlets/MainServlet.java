package com.tsystems.javaschool.loginov.logiweb.servlets;

import com.tsystems.javaschool.loginov.logiweb.models.Manager;
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
import java.util.List;

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

        if (!managerList.isEmpty()) {

            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("managerList", managerList);

            resp.sendRedirect("test.jsp");


//            req.setAttribute("managerList", managerList);
//
//            RequestDispatcher rd = getServletContext().getRequestDispatcher("/test.jsp");
//            rd.forward(req, resp);
        }
    }

    /**
     * Handles GET HTTP methods.
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
