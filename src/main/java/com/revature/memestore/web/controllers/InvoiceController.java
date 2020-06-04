package com.revature.memestore.web.controllers;

import com.revature.memestore.models.Invoice;
import com.revature.memestore.services.InvoiceService;
import com.revature.memestore.web.dtos.InvoiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService service){

        this.invoiceService = service;

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Invoice> getAllInvoices(){

        return invoiceService.getAllInvoices();

    }

    @GetMapping(value = "/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public Invoice getInvoiceById(@RequestParam int value){

        return invoiceService.getInvoiceById(value);

    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public InvoiceDto addNewInvoice(@RequestBody InvoiceDto newInvoiceDto){

        return invoiceService.addNewInvoice(newInvoiceDto);

    }

    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteInvoiceById(@RequestParam int id){

        return invoiceService.deleteInvoiceById(id);

    }

}
