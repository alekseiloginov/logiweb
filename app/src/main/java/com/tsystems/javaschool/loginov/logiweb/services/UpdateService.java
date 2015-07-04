package com.tsystems.javaschool.loginov.logiweb.services;

import com.tsystems.javaschool.loginov.logiweb.dao.AuthDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Updates an item in the database.
 */
public class UpdateService {

    public static final UpdateService INSTANCE = new UpdateService();

    private UpdateService() {
        if (UpdateService.INSTANCE != null) throw new InstantiationError("Creating of this object is not allowed.");
    }

    public static UpdateService getInstance() { return INSTANCE; }

    public Object updateItem(Object item) {
        SessionFactory sessionFactory = AuthDao.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        // Update
//
//        Query query1 = session.createQuery("from Manager where name = :name");
//        query1.setString("name", "Mihail");
//        List managerList1 = query1.list();
//
//        for (Object aManagerList1 : managerList1) {
//            Manager manager1 = (Manager) aManagerList1;
//            manager1.setName("Misha");
//            session.update(manager1);
//        }

        session.getTransaction().commit();
        return item;
    }
}
