package com.tsystems.javaschool.loginov.logiweb.listeners;

import com.tsystems.javaschool.loginov.logiweb.controllers.ControllerLocator;
import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.hibernate.SessionFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Servlet context listener implementation that initializes database connection
 * when application context is initialized.
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    private static Set<Method> userControllerMethods;
    private static Set<Method> truckControllerMethods;

    /**
     * Initializes database connection and Log4j configuration when application context is initialized.
     */
    public void contextInitialized(ServletContextEvent sce) {
        // Initializing hibernate SessionFactory
        AuthDao.getSessionFactory();

        ServletContext context = sce.getServletContext();

        // Initializing log4j configuration
        String log4jConfig = context.getInitParameter("log4j-config");
        if (log4jConfig == null) {
            System.err.println("No log4j-config init param, initializing log4j with BasicConfigurator");
            BasicConfigurator.configure();
        } else {
            String webAppPath = context.getRealPath("/WEB-INF/classes/");
            String log4jProp = webAppPath + log4jConfig;
            File log4jConfigFile = new File(log4jProp);
            if (log4jConfigFile.exists()) {
                System.out.println("Initializing log4j with: " + log4jProp);
                DOMConfigurator.configure(log4jProp);
            } else {
                System.err.println(log4jProp + " file not found, initializing log4j with BasicConfigurator");
                BasicConfigurator.configure();
            }
        }
        System.out.println("log4j configured properly");


        Logger logger = Logger.getLogger(AppContextListener.class);

        userControllerMethods = new HashSet<>();
        truckControllerMethods = new HashSet<>();

        try {
            Collections.addAll(userControllerMethods, AppContextListener.class
                    .getClassLoader()
                    .loadClass(("com.tsystems.javaschool.loginov.logiweb.controllers.UserController"))
                    .getMethods());

            Collections.addAll(truckControllerMethods, AppContextListener.class
                    .getClassLoader()
                    .loadClass(("com.tsystems.javaschool.loginov.logiweb.controllers.TruckController"))
                    .getMethods());

        } catch (ClassNotFoundException e) {
            logger.error("Class not found: " + AppContextListener.class, e);
        }

    }

    public static Set<Method> getTruckControllerMethods() {
        return truckControllerMethods;
    }

    public static Set<Method> getUserControllerMethods() {
        return userControllerMethods;
    }

    /**
     * Closes database connection when application context is destroyed.
     */
    public void contextDestroyed(ServletContextEvent sce) {
        SessionFactory sessionFactory =
                (SessionFactory) sce.getServletContext().getAttribute("SessionFactory");
        sessionFactory.close();
    }
}
