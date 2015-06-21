package com.tsystems.javaschool.loginov.logiweb.listeners;

import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import org.hibernate.SessionFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Servlet context listener implementation that initializes database connection
 * when application context is initialized.
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    /**
     * Initializes database connection when application context is initialized.
     */
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("AuthDao", AuthDao.getSessionFactory());

    }

    /**
     * Closes database connection when application context is destroyed.
     */
    public void contextDestroyed(ServletContextEvent sce) {
        SessionFactory sessionFactory =
                (SessionFactory) sce.getServletContext().getAttribute("AuthDao");
        sessionFactory.close();
    }
}
