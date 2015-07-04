package com.tsystems.javaschool.loginov.logiweb.controllers;

import com.tsystems.javaschool.loginov.logiweb.models.Location;
import com.tsystems.javaschool.loginov.logiweb.models.Truck;
import com.tsystems.javaschool.loginov.logiweb.services.ListService;
import com.tsystems.javaschool.loginov.logiweb.services.SaveService;
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

    public static final TruckController INSTANCE = new TruckController();

    private TruckController() {
        if (TruckController.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
        listService = ListService.getInstance();
        saveService = SaveService.getInstance();
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
        int driver_number = ((int[]) requestParameters.get("driver_number"))[0];
        int capacity = ((int[]) requestParameters.get("capacity"))[0];
        int drivable = ((int[]) requestParameters.get("drivable"))[0];
        Location location = ((Location[]) requestParameters.get("location"))[0];
        Map<String, Object> response = new HashMap<>();

        Truck truck = new Truck(plate_number, driver_number, capacity, drivable, location);

        Object savedTruck = saveService.saveItem(truck);
        response.put("data", savedTruck);
        return response;
    }
}
