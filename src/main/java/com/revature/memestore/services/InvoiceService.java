package com.revature.memestore.services;

import com.revature.memestore.models.Invoice;
import com.revature.memestore.repos.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class InvoiceService {

    private InvoiceRepository invoiceRepo;

    @Autowired
    public InvoiceService(InvoiceRepository repo){

        this.invoiceRepo = repo;

    }

    @Transactional(readOnly = true)
    public List<Invoice> getAllInvoices(){

        return invoiceRepo.getAll();

    }

    @Transactional
    public Invoice getInvoiceById(int id){

        return invoiceRepo.findById(id);

    }

    @Transactional
    public Invoice addNewInvoice(Invoice newInvoice){

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        newInvoice.setDate_ordered(formatter.format(date));

        return invoiceRepo.save(newInvoice);

    }

    @Transactional
    public boolean deleteInvoiceById(int id){

        return invoiceRepo.deleteById(id);

    }

}
