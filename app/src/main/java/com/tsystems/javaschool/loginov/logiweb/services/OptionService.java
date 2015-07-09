package com.tsystems.javaschool.loginov.logiweb.services;

import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import com.tsystems.javaschool.loginov.logiweb.models.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Fetches valid options to work with from the database.
 */
public class OptionService {

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

        // TODO handle situation when driversInCity == null
        if (driversInCity != null) {
            for (Object driverObject : driversInCity) {
                driverInCity = (Driver) driverObject;

                // check driversInCity list for if the driver is FREE + the driver's truck is NULL + worked_hours <= 176
                // TODO add an estimate shift time (G.maps API) + month's worked_hours check and month changes during the shift
                // TODO replace "0" with the estimate shift time in this month
                int estimateShiftTimeInThisMonth = 0;

                if (driverInCity.getTruck() == null &&
                        driverInCity.getWorked_hours() <= (176 - estimateShiftTimeInThisMonth)) {

                    validDriverSet.add(driverInCity);
                }
            }
        }

        // Creating a JSON string
        int optionCount = 0;
        String driverOptionJSONList = "[";

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

        return locationOptionJSONList;
    }
}
