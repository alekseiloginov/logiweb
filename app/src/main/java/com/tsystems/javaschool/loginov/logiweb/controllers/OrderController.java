package com.tsystems.javaschool.loginov.logiweb.controllers;

import com.tsystems.javaschool.loginov.logiweb.models.*;
import com.tsystems.javaschool.loginov.logiweb.services.ListService;
import com.tsystems.javaschool.loginov.logiweb.services.SaveService;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Homebrew MVC Controller implementation to work with the order data.
 */
public class OrderController {
    static Logger logger = Logger.getLogger(OrderController.class);
    private ListService listService;
    private SaveService saveService;

    public static final OrderController INSTANCE = new OrderController();

    private OrderController() {
        if (OrderController.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
        listService = ListService.getInstance();
        saveService = SaveService.getInstance();
    }

    public static OrderController getInstance() { return INSTANCE; }

    /**
     * Redirects user to the order page.
     */
    @RequestInfo(value = "Orders.do", method = "GET")
    public Map<String, Object> redirectToOrderPage(Map requestParameters) {
//        String role = ((String[]) requestParameters.get("role"))[0];
        Map<String, Object> response = new HashMap<>();

        String role = "manager"; // TODO add and check the hidden field from the redirected page via POST method

        if (role.equals("manager")) {
            response.put("page", "/WEB-INF/jsp/secure/manager/orders.jsp");
        } else {
            response.put("page", "/WEB-INF/jsp/secure/driver/orders.jsp");
        }
        return response;
    }

    /**
     * Fetches a list of all orders using the ListService and puts it to the response map.
     */
    @RequestInfo(value = "OrderList.do", method = "POST")
    public Map<String, Object> getAllOrders(Map requestParameters) {
        Map<String, Object> response = new HashMap<>();

        List orderList = listService.getAllItems("Order");
        response.put("data", orderList);
        return response;
    }

    /**
     * Adds an order to the database using the SaveService and puts the saved object back to the response map.
     */
    @RequestInfo(value = "OrderSave.do", method = "POST")
    public Map<String, Object> saveOrder(Map requestParameters) {
        int completed = Integer.parseInt(((String[]) requestParameters.get("completed"))[0]);
        String plate_number = ((String[]) requestParameters.get("truck"))[0];

        @SuppressWarnings("unchecked")
        Set<Driver> drivers = ((Set<Driver>[]) requestParameters.get("drivers"))[0];

        @SuppressWarnings("unchecked")
        Set<Waypoint> waypoints = ((Set<Waypoint>[]) requestParameters.get("waypoints"))[0];

        Map<String, Object> response = new HashMap<>();

        Object savedOrder = saveService.saveOrder(completed, plate_number, drivers, waypoints);

        response.put("datum", savedOrder);
        return response;
    }
}
