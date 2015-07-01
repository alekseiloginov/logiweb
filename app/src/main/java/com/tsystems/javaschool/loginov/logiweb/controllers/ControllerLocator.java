package com.tsystems.javaschool.loginov.logiweb.controllers;

import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * A single point of access to get any controller and returns a corresponding view as a string.
 */
public class ControllerLocator {

    public static Map<String, Object> execute(String requestUri, String requestMethod) {
        Logger logger = Logger.getLogger(ControllerLocator.class);

        try {
            for (Method method : ControllerLocator.class
                    .getClassLoader()
                    .loadClass(("com.tsystems.javaschool.loginov.logiweb.controllers.TruckController"))
                    .getMethods()) {

                try {
                    RequestInfo requestInfoAnnotation = method.getAnnotation(RequestInfo.class);

                    if (requestInfoAnnotation.value().equals(requestUri) &&
                        requestInfoAnnotation.method().equalsIgnoreCase(requestMethod)) {

                        logger.info("method to invoke = " + method);
                        Map<String, Object> response = (Map<String, Object>) method.invoke(null);

                        logger.info("response page = " + response);
                        return response;
                    }

                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
