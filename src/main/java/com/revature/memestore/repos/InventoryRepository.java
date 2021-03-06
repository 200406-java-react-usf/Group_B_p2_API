package com.revature.memestore.repos;


import com.revature.memestore.models.Inventory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class InventoryRepository implements CrudRepository<Inventory> {

    private SessionFactory sessionFactory;

    @Autowired
    public InventoryRepository(SessionFactory factory){
        this.sessionFactory = factory;
    }


    @Override
    public List<Inventory> getAll() {
        Session session = sessionFactory.getCurrentSession();
        try {
            return session.createQuery("FROM Inventory", Inventory.class).getResultList();
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    public Inventory findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        try {
            return session.get(Inventory.class, id);
        }catch(NoResultException e){
            return null;
        }
    }

    public Inventory findByItemName(String itemName){
            Session session = sessionFactory.getCurrentSession();
        try{
            return session.createQuery("from Inventory i where i.item_name = :name",Inventory.class).setParameter("name", itemName).getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public Inventory save(Inventory newObj) {
        Session session = sessionFactory.getCurrentSession();
        session.save(newObj);
        return newObj;
    }

    @Override
    public boolean update(Inventory updatedInventory) {

        Session session = sessionFactory.getCurrentSession();
        Inventory inventoryToUpdate = session.get(Inventory.class, updatedInventory.getItem_id());
        inventoryToUpdate.setCategory(updatedInventory.getCategory());
        inventoryToUpdate.setCost(updatedInventory.getCost());
        inventoryToUpdate.setDetails(updatedInventory.getDetails());
        inventoryToUpdate.setItem_image(updatedInventory.getItem_image());
        inventoryToUpdate.setItem_name(updatedInventory.getItem_name());
        return true;
    }

    @Override
    public boolean deleteById(int id) {

        Session session = sessionFactory.getCurrentSession();
        Inventory inventoryToDelete = session.get(Inventory.class, id);
        session.delete(inventoryToDelete);

        return true;
    }
}
