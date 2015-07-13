package com.tsystems.javaschool.loginov.logiweb.servlets;

import com.google.gson.Gson;
import com.tsystems.javaschool.loginov.logiweb.controllers.ControllerLocator;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Controller class that handles all client requests (and calls appropriate services).
 */
@WebServlet(name = "MainServlet", urlPatterns = {"*.do", "*.go"})
public class MainServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(MainServlet.class);
    private ControllerLocator controllerLocator = ControllerLocator.getInstance();

    /**
     * Handles POST HTTP methods.
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String resultPage = "/landing.html";
        String uri = req.getRequestURI().replaceAll("/", "");
        String method = req.getMethod();
        logger.info("Request: " + method + " " + uri);
        Map requestParameters = req.getParameterMap();
        HttpSession session = req.getSession(false);

        Map<String, Object> resultMap = controllerLocator.execute(this, uri, method, requestParameters);

        if (resultMap != null) {

            if (resultMap.containsKey("page")) {
                resultPage = (String) resultMap.get("page");
            }

            if (resultMap.containsKey("user")) {
                Object user = resultMap.get("user");
                session.setAttribute("user", user);
            }

            // Error for JTable: Operation with the database failed ("ERROR" in response)
            if (resultMap.containsKey("jTableError")) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                // JSON object for JTable to parse
                String response = "{\"Result\":\"ERROR\",\"Message\":\"" + resultMap.get("jTableError") + "\"}";
                resp.getWriter().write(response);
                logger.info("JSON response = " + response);
                return;
            }

            Gson gson = new Gson();

            // JSON for JTable: List of items fetched from the db ("Records" in response)
            if (resultMap.containsKey("data")) {
                Object data = resultMap.get("data");
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                // Create a JSON object for JTable to parse
                String response = "{\"Result\":\"OK\",\"Records\":" + gson.toJson(data) + "}";
                logger.info("JSON response = " + response);
                resp.getWriter().write(response);
                return;
            }

            // JSON for JTable: One item fetched from the db ("Record" in response)
            if (resultMap.containsKey("datum")) {
                Object datum = resultMap.get("datum");
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                // Create a JSON object for JTable to parse
                String response = "{\"Result\":\"OK\",\"Record\":" + gson.toJson(datum) + "}";
                logger.info("JSON response = " + response);
                resp.getWriter().write(response);
                return;
            }

            // JSON for JTable: Operation with the db (deletion) is successful ("OK" in response)
            if (resultMap.containsKey("OK")) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                // JSON object for JTable to parse
                String response = "{\"Result\":\"OK\"}";
                resp.getWriter().write(response);
                logger.info("JSON response = " + response);
                return;
            }

            // JSON for JTable: List of options fetched from the db ("Options" in response)
            if (resultMap.containsKey("options")) {
                Object options = resultMap.get("options");
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                // Create a JSON object for JTable to parse
                String response = "{\"Result\":\"OK\",\"Options\":" + options + "}";
                logger.info("JSON response = " + response);
                resp.getWriter().write(response);
                return;
            }

            if (resultMap.containsKey("error")) {
                Object error = resultMap.get("error");
                req.setAttribute("error", error);
            }

            if (resultMap.containsKey("success")) {
                Object success = resultMap.get("success");
                req.setAttribute("success", success);
            }
        }

        // Handle the case when user needs to be redirected to the requested page after login
        if (session != null && session.getAttribute("user") != null && session.getAttribute("from") != null) {
            resultPage = (String) session.getAttribute("from");
            session.removeAttribute("from");
            resp.sendRedirect(resultPage);
            return;
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(resultPage);
        dispatcher.forward(req, resp);
    }

    /**
     * Handles GET HTTP methods.
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String resultPage = "/landing.html";
        String uri = req.getRequestURI().replaceAll("/", "");
        String method = req.getMethod();
        logger.info("Request: " + method + " " + uri);

        Map requestParameters = req.getParameterMap();

        Map<String, Object> resultMap = controllerLocator.execute(this, uri, method, requestParameters);

        if (resultMap != null) {

            if (resultMap.containsKey("page")) {
                resultPage = (String) resultMap.get("page");
            }
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(resultPage);
        dispatcher.include(req, resp);
    }

}
