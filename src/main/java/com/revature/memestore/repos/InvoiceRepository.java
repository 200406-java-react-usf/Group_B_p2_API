package com.revature.memestore.repos;

import com.revature.memestore.models.Invoice;

import java.util.List;

public class InvoiceRepository implements CrudRepository<Invoice> {
    @Override
    public List<Invoice> getAll() {
        return null;
    }

    @Override
    public Invoice findById(int id) {
        return null;
    }

    @Override
    public Invoice save(Invoice newInvoice) {
        return null;
    }

    @Override
    public boolean update(Invoice updatedInvoice) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
