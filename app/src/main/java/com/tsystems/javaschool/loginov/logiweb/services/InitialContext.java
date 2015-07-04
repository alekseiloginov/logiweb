package com.tsystems.javaschool.loginov.logiweb.services;

import org.apache.log4j.Logger;

/**
 * Carries the reference to service used for JNDI lookup purpose.
 */
public class InitialContext {
    static Logger logger = Logger.getLogger(InitialContext.class);

    public Object lookup(String serviceName) {

        switch (serviceName) {
            case "DriverListService":
                logger.info("Looking up and creating a new DriverListService object");
                return new DriverListService();
            case "OrderListService":
                logger.info("Looking up and creating a new OrderListService object");
                return new OrderListService();
            case "WelcomePageService":
                logger.info("Looking up and creating a new WelcomePageService object");
                return new WelcomePageService();
        }
        return null;
    }
}
