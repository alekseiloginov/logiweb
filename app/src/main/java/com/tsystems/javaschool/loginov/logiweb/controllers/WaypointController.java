package com.tsystems.javaschool.loginov.logiweb.controllers;

import com.tsystems.javaschool.loginov.logiweb.models.Waypoint;
import com.tsystems.javaschool.loginov.logiweb.services.*;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Homebrew MVC Controller implementation to work with the waypoint data.
 */
public class WaypointController {
    private static Logger logger = Logger.getLogger(WaypointController.class);
    private ListService listService;
    private SaveService saveService;
//    private UpdateService updateService;
//    private DeleteService deleteService;
//    private OptionService optionService;

    public static final WaypointController INSTANCE = new WaypointController();

    private WaypointController() {
        if (WaypointController.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
        listService = ListService.getInstance();
        saveService = SaveService.getInstance();
//        updateService = UpdateService.getInstance();
//        deleteService = DeleteService.getInstance();
//        optionService = OptionService.getInstance();
    }

    public static WaypointController getInstance() { return INSTANCE; }

    /**
     * Fetches a list of waypoints for the required order using the ListService and puts it to the response map.
     */
    @RequestInfo(value = "OrderWaypointList.do", method = "POST")
    public Map<String, Object> getAllOrderWaypoints(Map requestParameters) {
        int orderID = Integer.parseInt(((String[]) requestParameters.get("orderID"))[0]);

        Map<String, Object> response = new HashMap<>();

        Set<Waypoint> waypointSet = listService.getAllOrderWaypoints(orderID);
        response.put("data", waypointSet);
        return response;
    }

    /**
     * Adds a waypoint to the required order using the SaveService and puts the saved waypoint back to the response map.
     */
    @RequestInfo(value = "OrderWaypointSave.do", method = "POST")
    public Map<String, Object> saveOrderWaypoint(Map requestParameters) {
        int orderID = Integer.parseInt(((String[]) requestParameters.get("orderID"))[0]);
        String waypointCity = ((String[]) requestParameters.get("location"))[0];
        String waypointFreightName = ((String[]) requestParameters.get("freight"))[0];

        Map<String, Object> response = new HashMap<>();

        Object savedOrderWaypoint = saveService.saveOrderWaypoint(orderID, waypointCity, waypointFreightName);

        response.put("datum", savedOrderWaypoint);
        return response;
    }
}
