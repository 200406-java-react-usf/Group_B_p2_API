package com.revature.memestore.repos;


import com.revature.memestore.models.Inventory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InventoryRepository implements CrudRepository<Inventory> {

    private SessionFactory sessionFactory;


    @Override
    public List<Inventory> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from inventory", Inventory.class).getResultList();
    }

    @Override
    public Inventory findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Inventory.class, id);
    }

    @Override
    public Inventory save(Inventory newObj) {
        Session session = sessionFactory.getCurrentSession();
        session.save(newObj);
        return newObj;
    }

    @Override
    public boolean update(Inventory updatedObj) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
