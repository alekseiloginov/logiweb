package com.tsystems.javaschool.loginov.logiweb.services;

import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import com.tsystems.javaschool.loginov.logiweb.exceptions.PasswordIncorrectException;
import com.tsystems.javaschool.loginov.logiweb.exceptions.UserNotFoundException;
import com.tsystems.javaschool.loginov.logiweb.models.Driver;
import com.tsystems.javaschool.loginov.logiweb.models.Manager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Authentication service.
 * Validates user input to log in.
 */
public class AuthService {
    static Logger logger = Logger.getLogger(AuthService.class);

    public static final AuthService INSTANCE = new AuthService();

    private AuthService() {
        if (AuthService.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
    }

    public static AuthService getInstance() { return INSTANCE; }

    /**
     * Authenticates a user.
     * Validates user input and returns founded user object,
     * otherwise throws custom exceptions.
     */
    public Object authenticate(String email, String password, String role)
            throws UserNotFoundException, PasswordIncorrectException {

        String encryptedPassword = null;
        logger.info("User's role is: " + role);

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

        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Criteria criteria;

        // Manager db check
        if (role.equals("manager")) {

            criteria = session.createCriteria(Manager.class);
            criteria.add(Restrictions.eq("email", email))
                    .setMaxResults(1);
            Manager manager = (Manager) criteria.uniqueResult();
            session.getTransaction().commit();

            if (manager != null) {
                session = sessionFactory.getCurrentSession();
                session.beginTransaction();

                criteria = session.createCriteria(Manager.class);
                criteria.add(Restrictions.eq("email", email))
                        .add(Restrictions.eq("password", encryptedPassword))
                        .setMaxResults(1);
                manager = (Manager) criteria.uniqueResult();

                session.getTransaction().commit();

                if (manager != null) {
                    logger.info("Manager found with details: " + manager);
                    return manager;

                } else {
                    throw new PasswordIncorrectException("Password incorrect for the given email", "Password incorrect");
                }

            } else {
                throw new UserNotFoundException("User not found in the database", "User not found");
            }

        // Driver db check
        } else {

            criteria = session.createCriteria(Driver.class);
            criteria.add(Restrictions.eq("email", email))
                    .setMaxResults(1);
            Driver driver = (Driver) criteria.uniqueResult();
            session.getTransaction().commit();

            if (driver != null) {

                session = sessionFactory.getCurrentSession();
                session.beginTransaction();

                criteria = session.createCriteria(Driver.class);
                criteria.add(Restrictions.eq("email", email))
                        .add(Restrictions.eq("password", encryptedPassword))
                        .setMaxResults(1);
                driver = (Driver) criteria.uniqueResult();
                session.getTransaction().commit();

                if (driver != null) {
                    logger.info("Driver found with details: " + driver);
                    return driver;

                } else {
                    throw new PasswordIncorrectException("Password incorrect for the given email", "Password incorrect");
                }

            } else {
                throw new UserNotFoundException("User not found", "User not found in the database");
            }
        }
    }
}
