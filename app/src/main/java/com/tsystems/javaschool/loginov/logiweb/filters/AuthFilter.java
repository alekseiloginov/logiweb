package com.tsystems.javaschool.loginov.logiweb.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter implementation for user validation.
 */
@WebFilter("/*")
public class AuthFilter implements Filter {
    private Logger logger = Logger.getLogger(AuthFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("AuthFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);  // returns null if no valid HttpSession (doesn't create a new one)
        String uri = req.getRequestURI();
        logger.info("Requested Resource: " + uri);



        // Logout: invalidates session and redirects to the landing page
        if (session != null && session.getAttribute("user") != null && uri.endsWith("Logout")) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("JSESSIONID")) {
                        logger.info("Invalidating session with JSESSIONID = " + cookie.getValue());
                        break;
                    }
                }
            }
            session.invalidate();
            resp.sendRedirect("/landing.html");

        } else if ((session == null || session.getAttribute("user") == null) && uri.contains("/secure/")) {
            session = req.getSession();
            session.setAttribute("from", uri);
            logger.error("Unauthorized access request");
            resp.sendRedirect("/login.html");

        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {}
}
