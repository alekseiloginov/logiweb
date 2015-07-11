package com.tsystems.javaschool.loginov.logiweb.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Application exception handler servlet.
 */
@WebServlet(name = "AppErrorHandler", urlPatterns = {"/AppErrorHandler"})
public class AppErrorHandler extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        if (servletName == null) servletName = "Unknown";
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) requestUri = "Unknown";

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.write("<html><head><title>Exception/Error Details</title>" +
                "<link rel=\"stylesheet\" href=\"css/style.css\"></head><body>");


        out.write("<br><br><br><br><br><div class=\"container\">");

        if (statusCode != 500){
            out.write("<h2>Error Details</h2><br><br>");
            out.write("<strong>Status Code</strong>: " + statusCode + "<br>");
            out.write("<strong>Requested URI</strong>: " + requestUri);

        } else {
            out.write("<h2>Exception Details</h2><br><br>");
            out.write("<ul><li>Servlet Name: " + servletName + "</li>");
            out.write("<li>Exception Name: " + throwable.getClass().getName() + "</li>");
            out.write("<li>Requested URI: " + requestUri + "</li>");
            out.write("<li>Exception Message: " + throwable.getMessage() + "</li>");
            out.write("</ul>");
        }
        out.write("<br><br>");
        out.write("<form action=\"/landing.html\" method=\"post\">\n" +
                "<input type=\"submit\" value=\"Go to the main page\">\n" +
                "</form>");

        out.write("</div>");
        out.write("</body></html>");
    }
}