package com.tsystems.javaschool.loginov.logiweb.services;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsRoute;
import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import com.tsystems.javaschool.loginov.logiweb.models.*;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Fetches valid options to work with from the database.
 */
public class OptionService {
    static Logger logger = Logger.getLogger(OptionService.class);

    public static final OptionService INSTANCE = new OptionService();

    private OptionService() {
        if (OptionService.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
    }

    public static OptionService getInstance() { return INSTANCE; }

    /**
     * Fetches all valid truck options from the database and returns them as a JSON string suitable for JTable.
     */
    public String getTruckOptions() {
        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        // get all drivable trucks
        List drivableTruckList = session.createQuery("from Truck where drivable = 1").list();
        // get all orders to check for occupied trucks
        List orderList = session.createQuery("from Order").list();
        session.getTransaction().commit();

        // check all drivable trucks if they are occupied with any other order
        Set<Truck> validTruckSet = new HashSet<>();
        Truck truck;
        Order order;

        for (Object truckObject : drivableTruckList) {
            truck = (Truck) truckObject;
            validTruckSet.add(truck);

            for (Object orderObject : orderList) {
                order = (Order) orderObject;

                if (order.getTruck().equals(truck)) {
                    validTruckSet.remove(truck);
                    break;
                }
            }
        }

        // Creating a JSON string
        int optionCount = 0;
        String truckOptionJSONList = "[";

        if (validTruckSet.size() == 0) {
            truckOptionJSONList += "]";

        } else {

            for (Truck validTruck : validTruckSet) {
                truckOptionJSONList += "{\"DisplayText\":\"";
                truckOptionJSONList += validTruck.getPlate_number();
                truckOptionJSONList += "\",\"Value\":\"";
                truckOptionJSONList += validTruck.getPlate_number();
                ++optionCount;

                if (optionCount < validTruckSet.size()) {
                    truckOptionJSONList += "\"},";
                } else {
                    truckOptionJSONList += "\"}]";
                }
            }
        }

        return truckOptionJSONList;
    }

    /**
     * Fetches all valid driver options from the database and returns them as a JSON string suitable for JTable.
     */
    public String getDriverOptions(int orderID) {
        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        // Fetch the order with the given orderID from the database
        Query orderQuery = session.createQuery("from Order where id = :orderID");
        orderQuery.setInteger("orderID", orderID);
        Order order = (Order) orderQuery.uniqueResult();

        session.getTransaction().commit();

        String orderTruckCity = order.getTruck().getLocation().getCity();
        int orderTruckDriverNumber = order.getTruck().getDriver_number();
        int orderOccupiedTruckDriverNumber = order.getDrivers().size();
        int orderTruckDriverLeft = orderTruckDriverNumber - orderOccupiedTruckDriverNumber;
        String freeDriverStatus = "free";
        List driversInCity = null;

        // if there is a space in the truck's shift for another driver
        if (orderTruckDriverLeft > 0) {

            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            // get a location object of the truck's city
            Query locationQuery = session.createQuery("from Location where city = :orderTruckCity");
            locationQuery.setString("orderTruckCity", orderTruckCity);
            Location dbLocation = (Location) locationQuery.uniqueResult();

            int locationID = dbLocation.getId();

            // get all FREE drivers from the same city
            Query driverQuery = session.createQuery("from Driver where location_id = :locationID and status =:status");
            driverQuery.setInteger("locationID", locationID);
            driverQuery.setString("status", freeDriverStatus);
            driversInCity = driverQuery.list();

            session.getTransaction().commit();
        }

        Set<Driver> validDriverSet = new HashSet<>();
        Driver driverInCity;

        // TODO handle situation when driversInCity == null  AND  order.getWaypoints() == null
        if (driversInCity != null && order.getWaypoints() != null) {

            // get all cities associated with this order and put them to the city set (excluding the origin city)
            Set<Waypoint> orderWaypointSet = order.getWaypoints();
            Set<String> citySet = new HashSet<>();

            for (Waypoint orderWaypoint : orderWaypointSet) {

                if (!orderWaypoint.getLocation().getCity().equals(orderTruckCity)) {
                    citySet.add( orderWaypoint.getLocation().getCity() );
                }
            }

            // create a string of collected cities to use in Google Maps API
            String waypointCities = "optimize:true";

            for (String city : citySet) {
                waypointCities += "|" + city;
            }

            // Google Maps API implementation

            GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyAPisnyi8SzMCy1NAZd7XMA6YBWYlFF9w4");
            DirectionsRoute[] routes = new DirectionsRoute[0];

            try {
                routes = DirectionsApi.newRequest(context)
                        .origin(orderTruckCity)                 // origin - city where the order's truck is located
                        .destination(orderTruckCity)
                        .optimizeWaypoints(true)
                        .waypoints(waypointCities)
                        .await();
            } catch (Exception e) {
                logger.error("Problem with the routes request using Google Maps API.", e);
            }

            DirectionsLeg[] legs = routes[0].legs;
            long durationInSeconds = 0;

            for (DirectionsLeg leg : legs) {
                durationInSeconds += leg.duration.inSeconds;
            }

            long orderDurationInHoursLong = Math.round(durationInSeconds / 60.0 / 60.0);
            int orderDurationInHoursInThisMonthInt = (int) orderDurationInHoursLong;

            // Check month changes during the order, start - this moment (if it start later - it'll pass too)

            // Start of the order DATE:TIME
            Date orderStartDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(orderStartDate);
            int orderStartMonth = cal.get(Calendar.MONTH);
            long orderStartLong = orderStartDate.getTime();

            // Start of a new month DATE:TIME
            Date date = new Date();
            cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.MONTH, orderStartMonth + 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            Date startOfTheNextMonthDate = cal.getTime();
            long startOfTheNextMonthLong = startOfTheNextMonthDate.getTime();
            long diffInMilliesLong = startOfTheNextMonthLong - orderStartLong;
            long diffInHoursLong = TimeUnit.HOURS.convert(diffInMilliesLong, TimeUnit.MILLISECONDS);

            if (diffInHoursLong < orderDurationInHoursLong) {
                orderDurationInHoursInThisMonthInt = (int) diffInHoursLong;
            }

            for (Object driverObject : driversInCity) {
                driverInCity = (Driver) driverObject;

                // check if the driver is FREE + driver's truck is NULL + driver's worked hours in this month <= 176
                if (driverInCity.getTruck() == null &&
                        (driverInCity.getWorked_hours() + orderDurationInHoursInThisMonthInt) <= 176) {

                    validDriverSet.add(driverInCity);
                }
            }
        }

        // Creating a JSON string
        int optionCount = 0;
        String driverOptionJSONList = "[";

        if (validDriverSet.size() == 0) {
            driverOptionJSONList += "]";

        } else {

            for (Driver validDriver : validDriverSet) {
                driverOptionJSONList += "{\"DisplayText\":\"";
                driverOptionJSONList += validDriver.getEmail();
                driverOptionJSONList += "\",\"Value\":\"";
                driverOptionJSONList += validDriver.getEmail();
                ++optionCount;

                if (optionCount < validDriverSet.size()) {
                    driverOptionJSONList += "\"},";
                } else {
                    driverOptionJSONList += "\"}]";
                }
            }
        }

        return driverOptionJSONList;
    }

    /**
     * Fetches all valid freight options from the database and returns them as a JSON string suitable for JTable.
     */
    public String getFreightOptions(int orderID, String city) {

        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        // get a location object of the chosen city
        Query locationQuery = session.createQuery("from Location where city = :city");
        locationQuery.setString("city", city);
        Location dbLocation = (Location) locationQuery.uniqueResult();

        int locationID = dbLocation.getId();

        // get all waypoints connected with this city
        Query waypointQuery = session.createQuery("from Waypoint where location_id = :locationID");
        waypointQuery.setInteger("locationID", locationID);
        List cityWaypointList = waypointQuery.list();

        // fetch all orders from the database to check them for the chosen waypoints
        List orderList = session.createQuery("from Order").list();

        // Fetch the order with the given orderID from the database
        Query orderQuery = session.createQuery("from Order where id = :orderID");
        orderQuery.setInteger("orderID", orderID);
        Order chosenOrder = (Order) orderQuery.uniqueResult();

        session.getTransaction().commit();

        // add all city waypoints to the validCityWaypointSet
        Set<Waypoint> validCityWaypointSet = new HashSet<>();
        Waypoint cityWaypointOption;
        Order order;

        for (Object cityWaypointObject : cityWaypointList) {
            cityWaypointOption = (Waypoint) cityWaypointObject;
            validCityWaypointSet.add(cityWaypointOption);

            // if other orders already contain this cityWaypointOption, remove it from the validCityWaypointSet
            for (Object orderObject : orderList) {
                order = (Order) orderObject;

                if (order.getWaypoints().contains(cityWaypointOption)) {
                    validCityWaypointSet.remove(cityWaypointOption);
                    break;
                }
            }
        }

        // get sum of all freight weights (loaded in the same city) that are already assign to the order's truck
        Set<Waypoint> chosenOrderWaypointSet = chosenOrder.getWaypoints();
        int orderAssignedFreightsWeight = 0;

        for (Waypoint chosenOrderWaypoint : chosenOrderWaypointSet) {
            if (chosenOrderWaypoint.getLocation().getCity().equals(city)
                    && chosenOrderWaypoint.getOperation().equals("loading")) {
                orderAssignedFreightsWeight += chosenOrderWaypoint.getFreight().getWeight();
            }
        }

        // check that operation is loading, freight status is 'prepared' and there's enough space for this freight
        int orderTruckCapacity = chosenOrder.getTruck().getCapacity();
        int freeFreightWeight = orderTruckCapacity - orderAssignedFreightsWeight;
        int freightWeight;

        for (Waypoint validCityWaypointOption : validCityWaypointSet) {
            freightWeight = validCityWaypointOption.getFreight().getWeight();

            if (validCityWaypointOption.getOperation().equals("loading") &&
                    validCityWaypointOption.getFreight().getStatus().equals("prepared") &&
                    freeFreightWeight < freightWeight) {

                validCityWaypointSet.remove(validCityWaypointOption);
            }
        }

        // Creating a JSON string
        int optionCount = 0;
        String freightOptionJSONList = "[";

        if (validCityWaypointSet.size() == 0) {
            freightOptionJSONList += "]";

        } else {
            for (Waypoint validCityWaypoint : validCityWaypointSet) {
                freightOptionJSONList += "{\"DisplayText\":\"";
                freightOptionJSONList += validCityWaypoint.getFreight().getName();
                freightOptionJSONList += "\",\"Value\":\"";
                freightOptionJSONList += validCityWaypoint.getFreight().getName();
                ++optionCount;

                if (optionCount < validCityWaypointSet.size()) {
                    freightOptionJSONList += "\"},";
                } else {
                    freightOptionJSONList += "\"}]";
                }
            }
        }

        return freightOptionJSONList;
    }

    /**
     * Fetches all valid location options from the database and returns them as a JSON string suitable for JTable.
     */
    public String getLocationOptions() {
        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List locationList = session.createQuery("from Location").list();
        session.getTransaction().commit();

        // Creating a JSON string
        int optionCount = 0;
        String locationOptionJSONList = "[";

        if (locationList.size() == 0) {
            locationOptionJSONList += "]";

        } else {

            for (Object location : locationList) {
                locationOptionJSONList += "{\"DisplayText\":\"";
                locationOptionJSONList += ((Location) location).getCity();
                locationOptionJSONList += "\",\"Value\":\"";
                locationOptionJSONList += ((Location) location).getCity();
                ++optionCount;

                if (optionCount < locationList.size()) {
                    locationOptionJSONList += "\"},";
                } else {
                    locationOptionJSONList += "\"}]";
                }
            }
        }
        return locationOptionJSONList;
    }
}
