package com.revature.memestore.services;

import com.revature.memestore.exceptions.BadRequestException;
import com.revature.memestore.exceptions.ResourceNotFoundException;
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

        List<Invoice> result = invoiceRepo.getAll();

        if(result.isEmpty()){
            throw new ResourceNotFoundException("No invoices found in the database");
        }

        return result;

    }

    @Transactional
    public Invoice getInvoiceById(int id){

        if(id <= 0){
            throw new BadRequestException("Invalid ID was input into getInvoiceById");
        }

        Invoice retrievedInvoice = invoiceRepo.findById(id);

        if(retrievedInvoice == null){
            throw new ResourceNotFoundException("No Invoice found with that ID");
        }

        return retrievedInvoice;

    }

    @Transactional
    public Invoice addNewInvoice(Invoice newInvoice){

        if(newInvoice.getUser_id() <= 0 || newInvoice.getTotal_cost() <= 0){
            throw new BadRequestException("Invalid userId or totalCost was input into addNewInvoice");
        }

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        newInvoice.setDate_ordered(formatter.format(date));

        return invoiceRepo.save(newInvoice);

    }

    @Transactional
    public boolean deleteInvoiceById(int id){

        if(id <= 0){
            throw new BadRequestException("Invalid ID was input into deleteInvoiceById");
        }

        Invoice retrievedInvoice = invoiceRepo.findById(id);

        if(retrievedInvoice == null){
            throw new ResourceNotFoundException("No Invoice found with that ID while trying to deleteInvoiceById");
        }

        return invoiceRepo.deleteById(id);

    }

}
