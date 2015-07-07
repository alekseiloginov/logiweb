package com.tsystems.javaschool.loginov.logiweb.services;

import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import com.tsystems.javaschool.loginov.logiweb.models.Order;
import com.tsystems.javaschool.loginov.logiweb.models.Truck;
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
     * Fetches valid truck options from the database and returns them as a JSON string.
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
        String truckOptionList = "[";

        for (Truck validTruck : validTruckSet) {
            truckOptionList += "{\"DisplayText\":\"";
            truckOptionList += validTruck.getPlate_number();
            truckOptionList += "\",\"Value\":\"";
            truckOptionList += validTruck.getPlate_number();
            ++optionCount;

            if (optionCount < validTruckSet.size()) {
                truckOptionList += "\"},";
            } else {
                truckOptionList += "\"}]";
            }
        }

        return truckOptionList;
    }
}
