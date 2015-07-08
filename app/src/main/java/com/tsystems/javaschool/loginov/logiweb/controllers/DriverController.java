package com.tsystems.javaschool.loginov.logiweb.controllers;

import com.tsystems.javaschool.loginov.logiweb.models.Driver;
import com.tsystems.javaschool.loginov.logiweb.services.*;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Homebrew MVC Controller implementation to work with the driver data.
 */
public class DriverController {
    static Logger logger = Logger.getLogger(DriverController.class);
    private ListService listService;
    private SaveService saveService;
    private UpdateService updateService;
    private DeleteService deleteService;
    private OptionService optionService;

    public static final DriverController INSTANCE = new DriverController();

    private DriverController() {
        if (DriverController.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
        listService = ListService.getInstance();
        saveService = SaveService.getInstance();
        updateService = UpdateService.getInstance();
        deleteService = DeleteService.getInstance();
        optionService = OptionService.getInstance();
    }

    public static DriverController getInstance() { return INSTANCE; }

    /**
     * Redirects user to the driver page.
     */
    @RequestInfo(value = "Drivers.do", method = "GET")
    public Map<String, Object> redirectToDriverPage(Map requestParameters) {
        Map<String, Object> response = new HashMap<>();

        response.put("page", "/WEB-INF/jsp/secure/manager/drivers.jsp");
        return response;
    }

    /**
     * Fetches a list of all drivers using the ListService and puts it to the response map.
     */
    @RequestInfo(value = "DriverList.do", method = "POST")
    public Map<String, Object> getAllDrivers(Map requestParameters) {
        String sorting = null;

        if (requestParameters.containsKey("jtSorting")) {
            sorting = ((String[]) requestParameters.get("jtSorting"))[0];
        }

        Map<String, Object> response = new HashMap<>();

        List driverList = listService.getAllItems("Driver", sorting);
        response.put("data", driverList);
        return response;
    }

    /**
     * Adds a driver to the database using the SaveService and puts the saved driver back to the response map.
     */
    @RequestInfo(value = "DriverSave.do", method = "POST")
    public Map<String, Object> saveDriver(Map requestParameters) {
        String name = ((String[]) requestParameters.get("name"))[0];
        String surname = ((String[]) requestParameters.get("surname"))[0];
        String email = ((String[]) requestParameters.get("email"))[0];
        String password = ((String[]) requestParameters.get("password"))[0];
        int worked_hours = Integer.parseInt(((String[]) requestParameters.get("worked_hours"))[0]);
        String status = ((String[]) requestParameters.get("status"))[0];
        String city = ((String[]) requestParameters.get("location"))[0];
        String plate_number = ((String[]) requestParameters.get("truck"))[0];

        Map<String, Object> response = new HashMap<>();

        Object savedDriver =
                saveService.saveDriver(name, surname, email, password, worked_hours, status, city, plate_number);

        response.put("datum", savedDriver);
        return response;
    }

    /**
     * Updates a driver in the database using the UpdateService and puts the updated driver back to the response map.
     */
    @RequestInfo(value = "DriverUpdate.do", method = "POST")
    public Map<String, Object> updateDriver(Map requestParameters) {
        int id = Integer.parseInt(((String[]) requestParameters.get("id"))[0]);
        String name = ((String[]) requestParameters.get("name"))[0];
        String surname = ((String[]) requestParameters.get("surname"))[0];
        String email = ((String[]) requestParameters.get("email"))[0];
        String password = ((String[]) requestParameters.get("password"))[0];
        int worked_hours = Integer.parseInt(((String[]) requestParameters.get("worked_hours"))[0]);
        String status = ((String[]) requestParameters.get("status"))[0];
        String city = ((String[]) requestParameters.get("location"))[0];
        String plate_number = ((String[]) requestParameters.get("truck"))[0];

        Map<String, Object> response = new HashMap<>();

        Object updatedDriver = updateService.updateDriver(id, name, surname, email, password,
                worked_hours, status, city, plate_number);

        response.put("datum", updatedDriver);
        return response;
    }

    /**
     * Deletes a driver from the database using the DeleteService and puts "OK" back to the response map.
     */
    @RequestInfo(value = "DriverDelete.do", method = "POST")
    public Map<String, Object> deleteDriver(Map requestParameters) {
        int id = Integer.parseInt(((String[]) requestParameters.get("id"))[0]);
        Map<String, Object> response = new HashMap<>();

        deleteService.deleteItem("Driver", id);

        response.put("OK", "OK");
        return response;
    }

    /**
     * Fetches a list of drivers for the required order's truck using the ListService and puts it to the response map.
     */
    @RequestInfo(value = "OrderTruckDriverList.do", method = "POST")
    public Map<String, Object> getAllOrderTruckDrivers(Map requestParameters) {
        int orderID = Integer.parseInt(((String[]) requestParameters.get("orderID"))[0]);

        Map<String, Object> response = new HashMap<>();

        Set<Driver> driverSet = listService.getAllOrderDrivers(orderID);
        response.put("data", driverSet);
        return response;
    }

    /**
     * Fetches a list of valid driver options using the OptionService and puts a returned JSON string to the response map.
     */
    @RequestInfo(value = "DriverOptions.do", method = "POST")
    public Map<String, Object> getAllDriverOptions(Map requestParameters) {
        int orderID = Integer.parseInt(((String[]) requestParameters.get("orderID"))[0]);

        Map<String, Object> response = new HashMap<>();

        String driverOptionList = optionService.getDriverOptions(orderID);
        response.put("options", driverOptionList);
        return response;
    }

    /**
     * Adds a driver the required order using the SaveService and puts the saved driver back to the response map.
     */
    @RequestInfo(value = "OrderTruckDriverSave.do", method = "POST")
    public Map<String, Object> saveOrderTruckDrivers(Map requestParameters) {
        int orderID = Integer.parseInt(((String[]) requestParameters.get("orderID"))[0]);
        String driverEmail = ((String[]) requestParameters.get("email"))[0];

        Map<String, Object> response = new HashMap<>();

        Object savedOrderDriver = saveService.saveOrderDriver(orderID, driverEmail);

        response.put("datum", savedOrderDriver);
        return response;
    }
}
