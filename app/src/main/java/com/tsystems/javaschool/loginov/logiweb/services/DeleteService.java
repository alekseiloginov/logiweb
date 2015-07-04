package com.tsystems.javaschool.loginov.logiweb.services;

import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Deletes an item (truck, driver, orders etc.) in the database.
 */
public class DeleteService {

    public static final DeleteService INSTANCE = new DeleteService();

    private DeleteService() {
        if (DeleteService.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
    }

    public static DeleteService getInstance() { return INSTANCE; }

    public Object deleteItem(Object item) {
        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        // Delete
//
//        Query query2 = session.createQuery("from Manager where name = :name");
//        query2.setString("name", "Aleksei");
//        List managerList2 = query2.list();
//
//        for (Object aManagerList2 : managerList2) {
//            Manager manager2 = (Manager) aManagerList2;
//            session.delete(manager2);
//        }

        session.getTransaction().commit();
        return item;
    }
}
