package com.revature.memestore.services;

import com.revature.memestore.exceptions.BadRequestException;
import com.revature.memestore.exceptions.ResourceNotFoundException;
import com.revature.memestore.models.Invoice;
import com.revature.memestore.models.User;
import com.revature.memestore.repos.InvoiceRepository;
import com.revature.memestore.repos.UserRepository;
import com.revature.memestore.web.dtos.InvoiceDto;
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
    public InvoiceDto addNewInvoice(InvoiceDto newInvoiceDto){

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        newInvoiceDto.setDate_ordered(formatter.format(date));

        Invoice newInvoice = new Invoice(newInvoiceDto);

        invoiceRepo.save(newInvoice, newInvoiceDto.getUser_id());

        return newInvoiceDto;

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
