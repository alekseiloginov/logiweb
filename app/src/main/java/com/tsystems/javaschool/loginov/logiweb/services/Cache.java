package com.tsystems.javaschool.loginov.logiweb.services;

import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

/**
 * Stores references of services to reuse them.
 */
public class Cache {
    static Logger logger = Logger.getLogger(Cache.class);
    private Set<Service> services;

    public Cache() {
        services = new HashSet<>();
    }

    public void addService(Service newService) {
        services.add(newService);
    }

    public Service getService(String serviceName) {
        for (Service service : services) {
            if (service.getName().equals(serviceName)) {
                logger.info("Returning cached " + serviceName + " object");
                return service;
            }
        }
        return null;
    }
}
