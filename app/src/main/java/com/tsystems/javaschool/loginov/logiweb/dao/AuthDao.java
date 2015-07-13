package com.tsystems.javaschool.loginov.logiweb.dao;

import com.tsystems.javaschool.loginov.logiweb.models.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.util.Properties;

/**
 * Utility class for creating SessionFactory from property based configuration.
 */
public class AuthDao {

    private AuthDao() {}

    private static SessionFactory sessionFactory;

    /**
     * Builds SessionFactory from database properties file.
     */
    private static SessionFactory buildSessionFactory() {

        try {
            Configuration configuration = new Configuration();

            Properties properties = new Properties();
            properties.load(AuthDao.class.getResourceAsStream("/db.properties"));
            configuration.setProperties(properties);

            configuration.addAnnotatedClass(Driver.class);
            configuration.addAnnotatedClass(DriverStatusChange.class);
            configuration.addAnnotatedClass(Freight.class);
            configuration.addAnnotatedClass(Location.class);
            configuration.addAnnotatedClass(Manager.class);
            configuration.addAnnotatedClass(Order.class);
            configuration.addAnnotatedClass(Truck.class);
            configuration.addAnnotatedClass(Waypoint.class);

            ServiceRegistry serviceRegistry =
                    new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

            return configuration.buildSessionFactory(serviceRegistry);

        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Provides SessionFactory or calls building if null.
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }
}
