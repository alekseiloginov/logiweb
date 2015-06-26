package com.tsystems.javaschool.loginov.logiweb.services;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Invalidates user session and forwards to the login page.
 */
public class LogoutService implements Service {
    static Logger logger = Logger.getLogger(LogoutService.class);

    public String getName() {
        return "LogoutService";
    }

    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    logger.info("JSESSIONID = " + cookie.getValue());
                    break;
                }
            }
        }

        HttpSession session = req.getSession(false);  // returns null if no valid HttpSession (doesn't create a new one)

        if(session != null) session.invalidate();
        return "/landing.html";
    }
}
