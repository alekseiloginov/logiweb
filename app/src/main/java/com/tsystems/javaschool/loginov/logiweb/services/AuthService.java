package com.tsystems.javaschool.loginov.logiweb.services;

import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import com.tsystems.javaschool.loginov.logiweb.models.Driver;
import com.tsystems.javaschool.loginov.logiweb.models.Manager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Authentication service.
 * Validates user input to log in and forwards to the home page if it's valid,
 * otherwise redirects back to the login page.
 */
public class AuthService implements Service {
    static Logger logger = Logger.getLogger(AuthService.class);

    public String getName() {
        return "AuthService";
    }

    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String enteredEmail = req.getParameter("email");
        String enteredPassword = req.getParameter("password");
        String encryptedPassword = null;
        String errorMsg = null;
        String role = req.getParameter("role");
        logger.info("User's role is: " + role);

        if (enteredPassword == null) errorMsg = "Password can't be null";
        else if (enteredPassword.equals("")) errorMsg = "Password can't be empty";

        if (enteredEmail == null) errorMsg = "Email can't be null";
        else if (enteredEmail.equals("")) errorMsg = "Email can't be empty";

        if (errorMsg != null) {
            resp.setContentType("text/html");
            out.println("</br></br>");
            out.println("<center><font color=red>" + errorMsg + "</font></center>");

            if (role.equals("manager")) {
                return "/login_manager.html";
            } else {
                return "/login_driver.html";
            }

        } else {
            // Password encryption using MD5
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(enteredPassword.getBytes());
                byte[] bytes = messageDigest.digest();
                StringBuilder stringBuilder = new StringBuilder();

                for (byte aByte : bytes) {
                    stringBuilder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
                }
                encryptedPassword = stringBuilder.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            SessionFactory sessionFactory = AuthDao.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();
            Transaction transaction = session.beginTransaction();

            Criteria criteria;
            HttpSession httpSession = req.getSession();

            // Manager db check
            if (role.equals("manager")) {

                // Set "role" session attribute as "manager" to use it later
                httpSession.setAttribute("role", "manager");

                criteria = session.createCriteria(Manager.class);
                criteria.add(Restrictions.eq("email", enteredEmail))
                        .add(Restrictions.eq("password", encryptedPassword))
                        .setMaxResults(1);
                Manager manager = (Manager) criteria.uniqueResult();
                transaction.commit();

                if (manager != null) {
                    httpSession = req.getSession();
                    httpSession.setAttribute("manager", manager);
                    logger.info("Manager found with details: " + manager);

                    if (httpSession.getAttribute("from") != null) {
                        String from = (String) httpSession.getAttribute("from");
                        httpSession.removeAttribute("from");
                        return from;
                    } else {
                        return "/welcome_manager.jsp";
                    }
                } else {
                    logger.error("Manager not found with email: " + enteredEmail);
                    resp.setContentType("text/html");
                    out.println("</br></br>");
                    out.println("<center><font color=red>Email/password not found</font></center>");
                    return "/login_manager.html";
                }

            // Driver db check
            } else {

                // Set "role" session attribute as "driver" to use it later
                httpSession.setAttribute("role", "driver");

                criteria = session.createCriteria(Driver.class);
                criteria.add(Restrictions.eq("email", enteredEmail))
                        .add(Restrictions.eq("password", encryptedPassword))
                        .setMaxResults(1);
                Driver driver = (Driver) criteria.uniqueResult();
                transaction.commit();

                if (driver != null) {
                    httpSession = req.getSession();
                    httpSession.setAttribute("driver", driver);
                    logger.info("Driver found with details: " + driver);

                    if (httpSession.getAttribute("from") != null) {
                        String from = (String) httpSession.getAttribute("from");
                        httpSession.removeAttribute("from");
                        return from;
                    } else {
                        return "/welcome_driver.jsp";
                    }
                } else {
                    logger.error("Driver not found with email: " + enteredEmail);
                    resp.setContentType("text/html");
                    out.println("</br></br>");
                    out.println("<center><font color=red>Email/password not found</font></center>");
                    return "/login_driver.html";
                }
            }
        }
    }
}
