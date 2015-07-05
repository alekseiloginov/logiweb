package com.tsystems.javaschool.loginov.logiweb.controllers;

import com.tsystems.javaschool.loginov.logiweb.models.Location;
import com.tsystems.javaschool.loginov.logiweb.models.Truck;
import com.tsystems.javaschool.loginov.logiweb.services.ListService;
import com.tsystems.javaschool.loginov.logiweb.services.SaveService;
import com.tsystems.javaschool.loginov.logiweb.services.UpdateService;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Homebrew MVC Controller implementation to work with the truck data.
 */
public class TruckController {
    static Logger logger = Logger.getLogger(TruckController.class);
    private ListService listService;
    private SaveService saveService;
    private UpdateService updateService;

    public static final TruckController INSTANCE = new TruckController();

    private TruckController() {
        if (TruckController.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
        listService = ListService.getInstance();
        saveService = SaveService.getInstance();
        updateService = UpdateService.getInstance();
    }

    public static TruckController getInstance() { return INSTANCE; }

    /**
     * Redirects user to the truck page.
     */
    @RequestInfo(value = "Trucks.do", method = "GET")
    public Map<String, Object> redirectToTruckPage(Map requestParameters) {
        Map<String, Object> response = new HashMap<>();

        response.put("page", "/WEB-INF/jsp/secure/manager/trucks.jsp");
        return response;
    }

    /**
     * Fetches a list of all trucks using the ListService and puts it to the response map.
     */
    @RequestInfo(value = "TruckList.do", method = "POST")
    public Map<String, Object> getAllTrucks(Map requestParameters) {
        Map<String, Object> response = new HashMap<>();

        List truckList = listService.getAllItems("Truck");
        response.put("data", truckList);
        return response;
    }

    /**
     * Adds a truck to the database using the SaveService and puts the saved object back to the response map.
     */
    @RequestInfo(value = "TruckSave.do", method = "POST")
    public Map<String, Object> saveTruck(Map requestParameters) {
        String plate_number = ((String[]) requestParameters.get("plate_number"))[0];
        int driver_number = Integer.parseInt(((String[]) requestParameters.get("driver_number"))[0]);
        int capacity = Integer.parseInt(((String[]) requestParameters.get("capacity"))[0]);
        int drivable = Integer.parseInt(((String[]) requestParameters.get("drivable"))[0]);
        String city = ((String[]) requestParameters.get("location"))[0];

        Map<String, Object> response = new HashMap<>();

        Object savedTruck = saveService.saveTruck(plate_number, driver_number, capacity, drivable, city);

        response.put("datum", savedTruck);
        return response;
    }

    /**
     * Updates a truck in the database using the UpdateService and puts "OK" back to the response map.
     */
    @RequestInfo(value = "TruckUpdate.do", method = "POST")
    public Map<String, Object> updateTruck(Map requestParameters) {
        int id = Integer.parseInt(((String[]) requestParameters.get("id"))[0]);
        String plate_number = ((String[]) requestParameters.get("plate_number"))[0];
        int driver_number = Integer.parseInt(((String[]) requestParameters.get("driver_number"))[0]);
        int capacity = Integer.parseInt(((String[]) requestParameters.get("capacity"))[0]);
        int drivable = Integer.parseInt(((String[]) requestParameters.get("drivable"))[0]);
        String city = ((String[]) requestParameters.get("location"))[0];

        Map<String, Object> response = new HashMap<>();

        updateService.updateTruck(id, plate_number, driver_number, capacity, drivable, city);

        response.put("OK", "OK");
        return response;
    }
}
