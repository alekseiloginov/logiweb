package com.tsystems.javaschool.loginov.logiweb.controllers;

import com.tsystems.javaschool.loginov.logiweb.services.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Homebrew MVC Controller implementation to work with the location data.
 */
public class LocationController {
//    private ListService listService;
//    private SaveService saveService;
//    private UpdateService updateService;
//    private DeleteService deleteService;
    private OptionService optionService;

    public static final LocationController INSTANCE = new LocationController();

    private LocationController() {
        if (LocationController.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
//        listService = ListService.getInstance();
//        saveService = SaveService.getInstance();
//        updateService = UpdateService.getInstance();
//        deleteService = DeleteService.getInstance();
        optionService = OptionService.getInstance();
    }

    public static LocationController getInstance() { return INSTANCE; }

    /**
     * Fetches all valid location options using the OptionService and puts a returned JSON string to the response map.
     */
    @RequestInfo(value = "LocationOptions.do", method = "POST")
    public Map<String, Object> getAllLocationOptions(Map requestParameters) {
        Map<String, Object> response = new HashMap<>();

        String locationOptionJSONList = optionService.getLocationOptions();
        response.put("options", locationOptionJSONList);
        return response;
    }
}
