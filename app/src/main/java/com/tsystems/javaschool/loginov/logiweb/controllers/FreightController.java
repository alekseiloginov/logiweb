package com.tsystems.javaschool.loginov.logiweb.controllers;

import com.tsystems.javaschool.loginov.logiweb.services.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Homebrew MVC Controller implementation to work with the freight data.
 */
public class FreightController {
    private ListService listService;
    private SaveService saveService;
    private UpdateService updateService;
    private DeleteService deleteService;
    private OptionService optionService;

    public static final FreightController INSTANCE = new FreightController();

    private FreightController() {
        if (FreightController.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
        listService = ListService.getInstance();
        saveService = SaveService.getInstance();
        updateService = UpdateService.getInstance();
        deleteService = DeleteService.getInstance();
        optionService = OptionService.getInstance();
    }

    public static FreightController getInstance() { return INSTANCE; }

    /**
     * Redirects user to the freights page.
     */
    @RequestInfo(value = "Freights.do", method = "GET")
    public Map<String, Object> redirectToDriverPage(Map requestParameters) {
        Map<String, Object> response = new HashMap<>();

        response.put("page", "/WEB-INF/jsp/secure/manager/freights.jsp");
        return response;
    }

    /**
     * Fetches a list of all freights using the ListService and puts it to the response map.
     */
    @RequestInfo(value = "FreightList.do", method = "POST")
    public Map<String, Object> getAllFreights(Map requestParameters) {
        String sorting = null;

        if (requestParameters.containsKey("jtSorting")) {
            sorting = ((String[]) requestParameters.get("jtSorting"))[0];
        }

        Map<String, Object> response = new HashMap<>();

        List freightList = listService.getAllItems("Freight", sorting);
        response.put("data", freightList);
        return response;
    }

    /**
     * Adds a freight to the database using the SaveService and puts the saved freight back to the response map.
     */
    @RequestInfo(value = "FreightSave.do", method = "POST")
    public Map<String, Object> saveFreight(Map requestParameters) {
        String name = ((String[]) requestParameters.get("name"))[0];
        int weight = Integer.parseInt(((String[]) requestParameters.get("weight"))[0]);
        String loadingLocation = ((String[]) requestParameters.get("loading"))[0];
        String unloadingLocation = ((String[]) requestParameters.get("unloading"))[0];
        String status = ((String[]) requestParameters.get("status"))[0];

        Map<String, Object> response = new HashMap<>();

        Object savedFreight = saveService.saveFreight(name, weight, loadingLocation, unloadingLocation, status);

        response.put("datum", savedFreight);
        return response;
    }

    /**
     * Fetches all valid freight options using the OptionService and puts a returned JSON string to the response map.
     */
    @RequestInfo(value = "FreightOptions.do", method = "POST")
    public Map<String, Object> getAllFreightOptions(Map requestParameters) {
        int orderID = Integer.parseInt(((String[]) requestParameters.get("orderID"))[0]);
        String city = ((String[]) requestParameters.get("city"))[0];

        Map<String, Object> response = new HashMap<>();

        String freightOptionJSONList = optionService.getFreightOptions(orderID, city);
        response.put("options", freightOptionJSONList);
        return response;
    }

//    /**
//     * Updates a truck in the database using the UpdateService and puts "OK" back to the response map.
//     */
//    @RequestInfo(value = "FreightUpdate.do", method = "POST")
//    public Map<String, Object> updateTruck(Map requestParameters) {
//        int id = Integer.parseInt(((String[]) requestParameters.get("id"))[0]);
//        String plate_number = ((String[]) requestParameters.get("plate_number"))[0];
//        int driver_number = Integer.parseInt(((String[]) requestParameters.get("driver_number"))[0]);
//        int capacity = Integer.parseInt(((String[]) requestParameters.get("capacity"))[0]);
//        int drivable = Integer.parseInt(((String[]) requestParameters.get("drivable"))[0]);
//        String city = ((String[]) requestParameters.get("location"))[0];
//
//        Map<String, Object> response = new HashMap<>();
//
//        Object updatedTruck = updateService.updateTruck(id, plate_number, driver_number, capacity, drivable, city);
//
//        response.put("datum", updatedTruck);
//        return response;
//    }

//    /**
//     * Deletes a truck from the database using the DeleteService and puts "OK" back to the response map.
//     */
//    @RequestInfo(value = "FreightDelete.do", method = "POST")
//    public Map<String, Object> deleteTruck(Map requestParameters) {
//        int id = Integer.parseInt(((String[]) requestParameters.get("id"))[0]);
//        Map<String, Object> response = new HashMap<>();
//
//        deleteService.deleteItem("Truck", id);
//
//        response.put("OK", "OK");
//        return response;
//    }
}
