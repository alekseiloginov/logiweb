package com.tsystems.javaschool.loginov.logiweb.services;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Returns a welcome page.
 */
public class WelcomePageService implements Service {
    static Logger logger = Logger.getLogger(WelcomePageService.class);

    public String getName() {
        return "WelcomePageService";
    }

    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession httpSession = req.getSession();
        String role = (String) httpSession.getAttribute("role");

        if (role.equals("manager")) {
            return "/WEB-INF/jsp/manager/welcome_manager.jsp";
        } else {
            return "/WEB-INF/jsp/driver/welcome_driver.jsp";
        }
    }
}
