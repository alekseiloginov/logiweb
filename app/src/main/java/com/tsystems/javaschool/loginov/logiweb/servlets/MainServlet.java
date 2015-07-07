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
    Logger logger = Logger.getLogger(MainServlet.class);
    ControllerLocator controllerLocator = ControllerLocator.getInstance();

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

            Gson gson = new Gson();

            // JSON for JTable: List of items fetched from the db ("Records" in response)
            if (resultMap.containsKey("data")) {
                Object data = resultMap.get("data");
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                String response;

                try {
                    // Create a JSON object for JTable to parse
                    response = "{\"Result\":\"OK\",\"Records\":" + gson.toJson(data) + "}";
                    logger.info("JSON response = " + response);
                } catch (Exception e) {
                    String error = "Data conversion into JSON object problem.";
                    response = "{\"Result\":\"ERROR\",\"Message\":" + error + "}";
//                response = "{\"Result\":\"ERROR\",\"Message\":\""+ error + "\"}";
                    logger.error(error, e);
                }
                resp.getWriter().write(response);
                return;
            }

            // JSON for JTable: One item fetched from the db ("Record" in response)
            if (resultMap.containsKey("datum")) {

                // TODO add check if resultMap contains any errors that we added after catch, like MySQLIntegrityConstraintViolationException: Duplicate entry
                Object datum = resultMap.get("datum");
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                String response;

                try {
                    // Create a JSON object for JTable to parse
                    response = "{\"Result\":\"OK\",\"Record\":" + gson.toJson(datum) + "}";
                    logger.info("JSON response = " + response);
                } catch (Exception e) {
                    String error = "Datum conversion into JSON object problem.";
                    response = "{\"Result\":\"ERROR\",\"Message\":" + error + "}";
//                    response = "{\"Result\":\"ERROR\",\"Message\":\""+ error + "\"}";
                    logger.error(error, e);
                }
                resp.getWriter().write(response);
                return;
            }

            // JSON for JTable: Operation with the db (deletion) is successful ("OK" in response)
            if (resultMap.containsKey("OK")) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                // JSON object for JTable to parse
                String response = "{\"Result\":\"OK\"}";
//                String response = "{\"Result\":\"ERROR\",\"Message\":\""+ "error message" + "\"}";
                resp.getWriter().write(response);
                logger.info("JSON response = " + response);
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

//            if (resultMap.containsKey("data")) {
//                Object data = resultMap.get("data");
//                resp.setContentType("application/json");
//                resp.setCharacterEncoding("UTF-8");
//                String response = new Gson().toJson(data);
//                String finalResponse = "{\"Result\":\"OK\",\"Records\":" + response + "}";
////                String finalResponse = "{\"Result\":\"ERROR\",\"Message\":\""+ "error message" + "\"}";
//                resp.getWriter().write(finalResponse);
//                return;
//            }
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(resultPage);
        dispatcher.include(req, resp);
    }

}
