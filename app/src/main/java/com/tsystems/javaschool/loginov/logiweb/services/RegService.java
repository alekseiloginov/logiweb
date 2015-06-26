package com.tsystems.javaschool.loginov.logiweb.services;

import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import com.tsystems.javaschool.loginov.logiweb.models.Manager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Registration service.
 * Validates user input to sign-up, if successful forwards to the login page,
 * otherwise redirects back to the sign-up page.
 */
public class RegService implements Service {
    static Logger logger = Logger.getLogger(RegService.class);

    public String getName() {
        return "RegService";
    }

    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String encryptedPassword = null;
        String errorMsg = null;

        if (password == null) errorMsg = "Password can't be null";
        else if (password.equals("")) errorMsg = "Password can't be empty";

        if (email == null) errorMsg = "Email can't be null";
        else if (email.equals("")) errorMsg = "Email can't be empty";

        if (surname == null) errorMsg = "Surname can't be null";
        else if (surname.equals("")) errorMsg = "Surname can't be empty";

        if (name == null) errorMsg = "Name can't be null";
        else if (name.equals("")) errorMsg = "Name can't be empty";

        if (errorMsg != null) {
            resp.setContentType("text/html");
            out.println("</br></br>");
            out.println("<center><font color=red>" + errorMsg + "</font></center>");
            return "/registration.html";

        } else {
            // Password encryption using MD5
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(password.getBytes());
                byte[] bytes = messageDigest.digest();
                StringBuilder stringBuilder = new StringBuilder();

                for (byte aByte : bytes) {
                    stringBuilder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
                }
                encryptedPassword = stringBuilder.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            Manager user = new Manager(name, surname, email, encryptedPassword);
            SessionFactory sessionFactory = AuthDao.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            logger.info("User registered with email: " + email + " and ID: " + user.getId());

            resp.setContentType("text/html");
            out.println("</br></br>");
            out.println("<center><font color=green>Registration successful!</font></center>");
            return "/login_manager.html";
        }
    }
}
