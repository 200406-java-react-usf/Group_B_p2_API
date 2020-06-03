package com.revature.memestore.repos;

import com.revature.memestore.models.Invoice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvoiceRepository implements CrudRepository<Invoice> {

    private SessionFactory sessionFactory;

    @Autowired
    public InvoiceRepository(SessionFactory factory){

        this.sessionFactory = factory;

    }

    @Override
    public List<Invoice> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Invoice", Invoice.class).getResultList();
    }

    @Override
    public Invoice findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Invoice.class, id);
    }

    @Override
    public Invoice save(Invoice newInvoice) {

        Session session = sessionFactory.getCurrentSession();
        session.save(newInvoice);

        return newInvoice;
    }

    @Override
    public boolean update(Invoice updatedInvoice) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Invoice invoiceToDelete = session.get(Invoice.class, id);
        session.delete(invoiceToDelete);
        return true;
    }
}
