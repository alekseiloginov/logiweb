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
    private Map<String, Object> response;

    /**
     * Fetches all trucks from the TruckListService and puts them with the page to view to the response map.
     */
    @RequestInfo(value = "TruckList.do", method = "GET")
    public Map<String, Object> getAllTrucks() {
        response = new HashMap<>();

        List truckList = new TruckListService().execute();

        response.put("data", truckList);
        response.put("page", "/WEB-INF/secure/jsp/manager/trucks.jsp");

        return response;
    }
}
