package com.revature.memestore.repos;

import com.revature.memestore.models.Inventory;
import com.revature.memestore.models.Invoice;
import com.revature.memestore.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvoiceRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public InvoiceRepository(SessionFactory factory){

        this.sessionFactory = factory;

    }

    public List<Invoice> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Invoice", Invoice.class).getResultList();
    }

    public Invoice findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Invoice.class, id);
    }

    public Invoice save(Invoice newInvoice, int user_id, List<Integer> item_ids) {

        Session session = sessionFactory.getCurrentSession();

        for(int id : item_ids){

            Inventory inventory = session.load(Inventory.class, id);

            newInvoice.addToItems(inventory);

        }

        User user = session.load(User.class, user_id);

        newInvoice.setUser(user);

        user.addToInvoices(newInvoice);

        session.save(newInvoice);

        return newInvoice;
    }

    public boolean update(Invoice updatedInvoice) {
        return false;
    }

    public boolean deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Invoice invoiceToDelete = session.get(Invoice.class, id);
        session.delete(invoiceToDelete);
        return true;
    }
}
