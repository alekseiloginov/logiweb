package com.tsystems.javaschool.loginov.logiweb.controllers;

import com.tsystems.javaschool.loginov.logiweb.services.AuthService;
import com.tsystems.javaschool.loginov.logiweb.services.RegService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * A single point of access to get any controller and returns a corresponding view as a string.
 */
public class ControllerLocator {
    static Logger logger = Logger.getLogger(ControllerLocator.class);

    public static final ControllerLocator INSTANCE = new ControllerLocator();

    private ControllerLocator() {
        if (ControllerLocator.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
    }

    public static ControllerLocator getInstance() { return INSTANCE; }

    /**
     * Invokes a method mapped to the given URI using reflexion, returns a result map or throws an exception.
     */
    public Map<String, Object> execute(HttpServlet httpServlet, String requestUri, String requestMethod,
                                       Map requestParameters) {

        @SuppressWarnings("unchecked")
        Map<String, Method> mappedMethods =
                (Map<String, Method>) httpServlet.getServletContext().getAttribute("mappedMethods");
        Method methodToInvoke = mappedMethods.get(requestUri);

        try {
            // if an annotated HTTP method equals to the request HTTP method
            if (methodToInvoke.getAnnotation(RequestInfo.class).method().equalsIgnoreCase(requestMethod)) {
                logger.info("Method to invoke: " + methodToInvoke);

                // Create a singleton controller instance
                Method getInstance = methodToInvoke.getDeclaringClass().getMethod("getInstance");
                Object classInstance = getInstance.invoke(null);
                logger.info("Class instance for the method to invoke: " + classInstance);

                @SuppressWarnings("unchecked")
                Map<String, Object> result =
                        (Map<String, Object>) methodToInvoke.invoke(classInstance, requestParameters);
                return result;
            }

        } catch (Throwable e) {
            logger.error("Problem with the annotated method: " + methodToInvoke, e);
        }

        return null;
    }
}
