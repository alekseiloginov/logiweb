package com.tsystems.javaschool.loginov.logiweb.services;

import org.apache.log4j.Logger;

/**
 * Carries the reference to service used for JNDI lookup purpose.
 */
public class InitialContext {
    static Logger logger = Logger.getLogger(InitialContext.class);

    public Object lookup(String serviceName) {

        switch (serviceName) {
            case "AuthService":
                logger.info("Looking up and creating a new AuthService object");
                return new AuthService();
            case "RegService":
                logger.info("Looking up and creating a new RegService object");
                return new RegService();
            case "LogoutService":
                logger.info("Looking up and creating a new LogoutService object");
                return new LogoutService();
        }
        return null;
    }
}
