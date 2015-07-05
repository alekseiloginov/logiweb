package com.tsystems.javaschool.loginov.logiweb.services;

import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import com.tsystems.javaschool.loginov.logiweb.exceptions.UsedEmailException;
import com.tsystems.javaschool.loginov.logiweb.models.Manager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Registration service.
 * Validates user input to sign-up.
 */
public class RegService {
    static Logger logger = Logger.getLogger(RegService.class);

    public static final RegService INSTANCE = new RegService();

    private RegService() {
        if (RegService.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
    }

    public static RegService getInstance() { return INSTANCE; }

    /**
     * Registers a manager.
     * Validates manager's input, if successful returns true,
     * otherwise throws a custom exception.
     */
    public void register(String name, String surname, String email, String password) throws UsedEmailException {
        String encryptedPassword = null;

        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Criteria criteria = session.createCriteria(Manager.class);
        criteria.add(Restrictions.eq("email", email))
                .setMaxResults(1);
        Manager manager = (Manager) criteria.uniqueResult();
        session.getTransaction().commit();

        if (manager != null) {
            throw new UsedEmailException("This email is already used", "Used email");
        }

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
        session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        logger.info("Manager registered with the email: " + email + " and ID: " + user.getId());
    }
}
