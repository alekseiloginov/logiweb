package com.tsystems.javaschool.loginov.logiweb.services;

import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Deletes an item (truck, driver, orders etc.) from the database.
 */
public class DeleteService {
    private static Logger logger = Logger.getLogger(DeleteService.class);

    public static final DeleteService INSTANCE = new DeleteService();

    private DeleteService() {
        if (DeleteService.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
    }

    public static DeleteService getInstance() { return INSTANCE; }

    /**
     * Deletes a provided item with the given ID from the database.
     */
    public void deleteItem(String item, int id) {
        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Query query = session.createQuery("from " + item + " where id = :id");
        query.setInteger("id", id);
        Object itemToDelete = query.uniqueResult();
        logger.info(item + " to delete: " + itemToDelete);

        session.delete(itemToDelete);
        logger.info(item + " with ID: " + id + " successfully deleted!");

        session.getTransaction().commit();
    }
}
