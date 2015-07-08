package com.tsystems.javaschool.loginov.logiweb.services;

import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import com.tsystems.javaschool.loginov.logiweb.models.Driver;
import com.tsystems.javaschool.loginov.logiweb.models.Location;
import com.tsystems.javaschool.loginov.logiweb.models.Order;
import com.tsystems.javaschool.loginov.logiweb.models.Truck;
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
     * Fetches all valid truck options from the database and returns them as a JSON string.
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
     * Fetches all valid driver options from the database and returns them as a JSON string.
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
}
