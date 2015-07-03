package com.tsystems.javaschool.loginov.logiweb.controllers;

import com.tsystems.javaschool.loginov.logiweb.listeners.AppContextListener;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * A single point of access to get any controller and returns a corresponding view as a string.
 */
public class ControllerLocator {
    static Logger logger = Logger.getLogger(ControllerLocator.class);

    public Map<String, Object> execute(String requestUri, String requestMethod, Map requestParameters) {


        // Login or Registration request

        if (requestUri.equals("Login.do") || requestUri.equals("Register.do") || requestUri.equals("Logout.do")) {

            Set<Method> userControllerMethods = AppContextListener.getUserControllerMethods();
            UserController userController = new UserController();

            for (Method method : userControllerMethods) {

                try {
                    RequestInfo requestInfoAnnotation = method.getAnnotation(RequestInfo.class);

                    if (requestInfoAnnotation.value().equals(requestUri) &&
                        requestInfoAnnotation.method().equalsIgnoreCase(requestMethod)) {

                        return (Map<String, Object>) method.invoke(userController, requestParameters);
                    }

                } catch (Throwable e) {
                    logger.error("Problem with the annotated method: " + method, e);
                }
            }


        // TruckList request

        } else if (requestUri.equals("TruckList.do")) {

            Set<Method> truckControllerMethods = AppContextListener.getTruckControllerMethods();
            TruckController truckController = new TruckController();

            for (Method method : truckControllerMethods) {

                try {
                    RequestInfo requestInfoAnnotation = method.getAnnotation(RequestInfo.class);

                    if (requestInfoAnnotation.value().equals(requestUri) &&
                            requestInfoAnnotation.method().equalsIgnoreCase(requestMethod)) {

                        return (Map<String, Object>) method.invoke(truckController);
                    }

                } catch (Throwable e) {
                    logger.error("Problem with the annotated method: " + method, e);
                }
            }
        }

        return null;
    }
}
