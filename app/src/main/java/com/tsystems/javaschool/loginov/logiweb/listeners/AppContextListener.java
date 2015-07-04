package com.tsystems.javaschool.loginov.logiweb.listeners;

import com.tsystems.javaschool.loginov.logiweb.controllers.RequestInfo;
import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.hibernate.SessionFactory;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Servlet context listener implementation that initializes database connection
 * when application context is initialized.
 */
@WebListener
public class AppContextListener implements ServletContextListener {

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

        // Parsing controllers package for the RequestInfo annotation methods and creating a map URI-method
        Reflections reflections = new Reflections(
                new ConfigurationBuilder().setUrls(
                        ClasspathHelper.forPackage("com.tsystems.javaschool.loginov.logiweb.controllers") ).setScanners(
                        new MethodAnnotationsScanner() ) );
        Set<Method> methods = reflections.getMethodsAnnotatedWith(RequestInfo.class);
        Map<String, Method> mappedMethods = new HashMap<>();

        for (Method method : methods) {
            mappedMethods.put(method.getAnnotation(RequestInfo.class).value(), method);

            // Create singleton instances of all controllers
            try {
                Method getInstance = method.getDeclaringClass().getMethod("getInstance");
                Object classInstance = getInstance.invoke(null);

            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                logger.error("Problem with creating an instance of the class: " + method.getDeclaringClass(), e);
            }

        }
        logger.info("Parsed annotated method map: " + mappedMethods);
        context.setAttribute("mappedMethods", mappedMethods);
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
