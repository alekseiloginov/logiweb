package com.tsystems.javaschool.loginov.logiweb.controllers;

import com.tsystems.javaschool.loginov.logiweb.services.*;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Homebrew MVC Controller implementation to work with the waypoint data.
 */
public class WaypointController {
    static Logger logger = Logger.getLogger(WaypointController.class);
    private ListService listService;
    private SaveService saveService;
    private UpdateService updateService;
    private DeleteService deleteService;
    private OptionService optionService;

    public static final WaypointController INSTANCE = new WaypointController();

    private WaypointController() {
        if (WaypointController.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
        listService = ListService.getInstance();
        saveService = SaveService.getInstance();
        updateService = UpdateService.getInstance();
        deleteService = DeleteService.getInstance();
        optionService = OptionService.getInstance();
    }

    public static WaypointController getInstance() { return INSTANCE; }

//    /**
//     * Redirects user to the waypoint page.
//     */
//    @RequestInfo(value = "Waypoints.do", method = "GET")
//    public Map<String, Object> redirectToDriverPage(Map requestParameters) {
//        Map<String, Object> response = new HashMap<>();
//
//        response.put("page", "/WEB-INF/jsp/secure/manager/waypoints.jsp");
//        return response;
//    }
}
