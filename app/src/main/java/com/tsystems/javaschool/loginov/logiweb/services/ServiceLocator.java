package com.tsystems.javaschool.loginov.logiweb.services;

/**
 * A single point of contact to get services by JNDI lookup caching the services.
 */
public class ServiceLocator {
    private static Cache cache;

    static {
        cache = new Cache();
    }

    public static Service getService(String serviceName) {
        Service service = cache.getService(serviceName);

        if (service != null) {
            return service;
        }

        InitialContext context = new InitialContext();
        Service service1 = (Service) context.lookup(serviceName);
        cache.addService(service1);
        return service1;
    }
}
