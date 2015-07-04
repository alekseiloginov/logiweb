package com.tsystems.javaschool.loginov.logiweb.controllers;

import com.tsystems.javaschool.loginov.logiweb.exceptions.PasswordIncorrectException;
import com.tsystems.javaschool.loginov.logiweb.exceptions.UsedEmailException;
import com.tsystems.javaschool.loginov.logiweb.exceptions.UserNotFoundException;
import com.tsystems.javaschool.loginov.logiweb.services.AuthService;
import com.tsystems.javaschool.loginov.logiweb.services.RegService;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Homebrew MVC Controller implementation to work with the user data.
 */
public class UserController {
    static Logger logger = Logger.getLogger(UserController.class);
    private AuthService authService;
    private RegService regService;

    public static final UserController INSTANCE = new UserController();

    private UserController() {
        if (UserController.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
        authService = AuthService.getInstance();
        regService = RegService.getInstance();
    }

    public static UserController getInstance() { return INSTANCE; }

    /**
     * Validates user's log in input via AuthService, catches any exceptions
     * and puts the page to view with any error messages to the response map.
     */
    @RequestInfo(value = "Login.do", method = "POST")
    public Map<String, Object> authenticate(Map requestParameters) {
        String email = ((String[]) requestParameters.get("email"))[0];
        String password = ((String[]) requestParameters.get("password"))[0];
        String role = ((String[]) requestParameters.get("role"))[0];
        Map<String, Object> response = new HashMap<>();
        logger.info("Fetching user with the email: " + email + ", role: " + role);
        Object user;

        try {
            user = authService.authenticate(email, password, role);
            response.put("user", user);

            if (role.equals("manager")) {
                response.put("page", "/WEB-INF/jsp/secure/manager/welcome_manager.jsp");
            } else {
                response.put("page", "/WEB-INF/jsp/secure/driver/welcome_driver.jsp");
            }

        } catch (UserNotFoundException e) {
            logger.error("User not found with email: " + email, e);
            response.put("error", "User not found in the database");

            if (role.equals("manager")) {
                response.put("page", "/login_manager.jsp");
            } else {
                response.put("page", "/login_driver.jsp");
            }

        } catch (PasswordIncorrectException e) {
            logger.error("Password incorrect for email: " + email, e);
            response.put("error", "Password incorrect for the given email");

            if (role.equals("manager")) {
                response.put("page", "/login_manager.jsp");
            } else {
                response.put("page", "/login_driver.jsp");
            }
        }
        return response;
    }

    /**
     * Validates user's sign-up input via RegService, catches any exceptions
     * and puts the page to view with the success or error message to the response map.
     */
    @RequestInfo(value = "Register.do", method = "POST")
    public Map<String, Object> register(Map requestParameters) {
        String name = ((String[]) requestParameters.get("name"))[0];
        String surname = ((String[]) requestParameters.get("surname"))[0];
        String email = ((String[]) requestParameters.get("email"))[0];
        String password = ((String[]) requestParameters.get("password"))[0];
        Map<String, Object> response = new HashMap<>();

        logger.info("Saving manager with the name: " + name + ", surname: " + surname + ", email: " + email);

        try {
            regService.register(name, surname, email, password);
            response.put("success", "Registration successful!");
            response.put("page", "/login_manager.jsp");

        } catch (UsedEmailException e) {
            logger.error("Manager tries to sign-up with the already used email: " + email, e);
            response.put("error", "This email is already used");
            response.put("page", "/registration.jsp");
        }

        return response;

    }
}