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
 * Fetches all orders from the database.
 */
public class OrderListService implements Service {
    static Logger logger = Logger.getLogger(OrderListService.class);

    public String getName() {
        return "OrderListService";
    }

    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        // Criteria style
//        List orderList = session.createCriteria(Order.class).list();

        Query query;
        session.beginTransaction();

        // Get order list
        query = session.createQuery("from Order");
        List orderList = query.list();

        session.getTransaction().commit();

        req.setAttribute("orderList", orderList);

        HttpSession httpSession = req.getSession();
        String role = (String) httpSession.getAttribute("role");

        if (role.equals("manager")) {
            return "/WEB-INF/jsp/manager/orders.jsp";
        } else {
            return "/WEB-INF/jsp/driver/orders_driver.jsp";
        }
    }
}
