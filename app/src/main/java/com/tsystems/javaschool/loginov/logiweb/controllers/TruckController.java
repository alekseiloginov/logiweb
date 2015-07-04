package com.tsystems.javaschool.loginov.logiweb.controllers;

import com.tsystems.javaschool.loginov.logiweb.services.TruckListService;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Homebrew MVC Controller implementation to work with the truck data.
 */
public class TruckController {
    static Logger logger = Logger.getLogger(TruckController.class);
    private TruckListService truckListService;

    public static final TruckController INSTANCE = new TruckController();

    private TruckController() {
        if (TruckController.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
        truckListService = TruckListService.getInstance();
    }

    public static TruckController getInstance() { return INSTANCE; }

    /**
     * Fetches all trucks from the TruckListService and puts them with the page to view to the response map.
     */
    @RequestInfo(value = "TruckList.do", method = "GET")
    public Map<String, Object> getAllTrucks(Map requestParameters) {
        Map<String, Object> response = new HashMap<>();

        List truckList = truckListService.getAllTrucks();

        response.put("data", truckList);
        response.put("page", "/WEB-INF/jsp/secure/manager/trucks.jsp");

        return response;
    }
}
