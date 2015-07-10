package com.tsystems.javaschool.loginov.logiweb.controllers;

import com.tsystems.javaschool.loginov.logiweb.services.DeleteService;
import com.tsystems.javaschool.loginov.logiweb.services.ListService;
import com.tsystems.javaschool.loginov.logiweb.services.SaveService;
import com.tsystems.javaschool.loginov.logiweb.services.UpdateService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Homebrew MVC Controller implementation to work with the order data.
 */
public class OrderController {
    private ListService listService;
    private SaveService saveService;
    private UpdateService updateService;
    private DeleteService deleteService;

    public static final OrderController INSTANCE = new OrderController();

    private OrderController() {
        if (OrderController.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
        listService = ListService.getInstance();
        saveService = SaveService.getInstance();
        updateService = UpdateService.getInstance();
        deleteService = DeleteService.getInstance();
    }

    public static OrderController getInstance() { return INSTANCE; }

    /**
     * Redirects user to the order page.
     */
    @RequestInfo(value = "Orders.do", method = "GET")
    public Map<String, Object> redirectToOrderPage(Map requestParameters) {
        String role = ((String[]) requestParameters.get("role"))[0];
        Map<String, Object> response = new HashMap<>();

        if (role.equals("manager")) {
            response.put("page", "/WEB-INF/jsp/secure/manager/orders.jsp");
        } else if (role.equals("driver")) {
            response.put("page", "/WEB-INF/jsp/secure/driver/orders.jsp");
        }
        return response;
    }

    /**
     * Fetches a list of all orders using the ListService and puts it to the response map.
     */
    @RequestInfo(value = "OrderList.do", method = "POST")
    public Map<String, Object> getAllOrders(Map requestParameters) {
        String role = ((String[]) requestParameters.get("role"))[0];
        List orderList = null;

        Map<String, Object> response = new HashMap<>();

        if (role.equals("manager")) {
            String sorting = null;
            if (requestParameters.containsKey("jtSorting")) {
                sorting = ((String[]) requestParameters.get("jtSorting"))[0];
            }
            orderList = listService.getAllItems("Order", sorting);

        } else if (role.equals("driver")) {
            int truckID = Integer.parseInt(((String[]) requestParameters.get("truckID"))[0]);
            orderList = listService.getAllDriverOrders(truckID);
        }

        // if orderList != null ... else ...
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

        Map<String, Object> response = new HashMap<>();

        Object savedOrder = saveService.saveOrder(plate_number, completed);

        response.put("datum", savedOrder);
        return response;
    }

    /**
     * Updates an order in the database using the UpdateService and puts the updated order back to the response map.
     */
    @RequestInfo(value = "OrderUpdate.do", method = "POST")
    public Map<String, Object> updateOrder(Map requestParameters) {
        int id = Integer.parseInt(((String[]) requestParameters.get("id"))[0]);
        int completed = Integer.parseInt(((String[]) requestParameters.get("completed"))[0]);
        String plate_number = ((String[]) requestParameters.get("truck"))[0];

        Map<String, Object> response = new HashMap<>();

        Object updatedOrder = updateService.updateOrder(id, plate_number, completed);

        response.put("datum", updatedOrder);
        return response;
    }

    /**
     * Deletes an order from the database using the DeleteService and puts "OK" back to the response map.
     */
    @RequestInfo(value = "OrderDelete.do", method = "POST")
    public Map<String, Object> deleteOrder(Map requestParameters) {
        int id = Integer.parseInt(((String[]) requestParameters.get("id"))[0]);
        Map<String, Object> response = new HashMap<>();

        deleteService.deleteItem("Order", id);

        response.put("OK", "OK");
        return response;
    }
}
