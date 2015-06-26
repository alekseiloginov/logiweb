package com.tsystems.javaschool.loginov.logiweb.services;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Defines methods that all services must implement.
 */
public interface Service {

    /**
     * Returns a name of a service.
     */
    String getName();

    /**
     * Executes a certain service with provided request and response,
     * and returns a string with the page to show.
     */
    String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
