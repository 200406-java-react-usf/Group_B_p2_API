package com.revature.memestore.services;

import com.revature.memestore.exceptions.BadRequestException;
import com.revature.memestore.exceptions.ResourceNotFoundException;
import com.revature.memestore.models.Invoice;
import com.revature.memestore.models.User;
import com.revature.memestore.models.UserRole;
import com.revature.memestore.repos.InvoiceRepository;
import com.revature.memestore.web.dtos.InvoiceDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnListOfInvoices(){

        User mockUser = new User(1,"test1", "password", "test1@me.com", "test", "one", UserRole.USER);

        List<Invoice> mockInvoices = new ArrayList<Invoice>();
        mockInvoices.add(new Invoice(1,mockUser,100F,"1/1/2020 1:00:00"));
        mockInvoices.add(new Invoice(2,mockUser,200F,"1/1/2020 1:00:00"));
        mockInvoices.add(new Invoice(3,mockUser,300F,"1/1/2020 1:00:00"));

        when(invoiceRepository.getAll()).thenReturn(mockInvoices);

        List<Invoice> result = invoiceService.getAllInvoices();

        assertEquals(result, mockInvoices);

    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenDatabaseIsEmpty(){

        List<Invoice> mockInvoices = new ArrayList<Invoice>();

        when(invoiceRepository.getAll()).thenReturn(mockInvoices);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {

            List<Invoice> result = invoiceService.getAllInvoices();

        });

    }

    @Test
    public void shouldReturnAnInvoiceWhenGetByIdGetsAValidId(){

        User mockUser = new User(1,"test1", "password", "test1@me.com", "test", "one", UserRole.USER);
        Invoice mockInvoice = new Invoice(1,mockUser,100F,"1/1/2020 1:00:00");

        when(invoiceRepository.findById(1)).thenReturn(mockInvoice);

        Invoice result = invoiceService.getInvoiceById(1);

        assertEquals(result, mockInvoice);

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenGivenABadId(){

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            invoiceService.getInvoiceById(0);
        });

    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenNoUserIsFound(){

        when(invoiceRepository.findById(100)).thenReturn(null);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
           Invoice result = invoiceService.getInvoiceById(100);
        });

    }

    @Test
    public void shouldAddANewInvoiceIfCorrectInvoiceIsGiven(){

        User mockUser = new User(1,"test1", "password", "test1@me.com", "test", "one", UserRole.USER);
        Invoice mockInvoice = new Invoice(1,mockUser,100F,"1/1/2020 1:00:00");
        InvoiceDto mockDto = new InvoiceDto(1,100F,"1/1/2020 1:00:00");

        when(invoiceRepository.save(mockInvoice, mockDto.getUser_id(), mockDto.getItems())).thenReturn(mockInvoice);

        InvoiceDto result = invoiceService.addNewInvoice(mockDto);

        assertEquals(result,mockDto);

    }

    @Test
    public void shouldDeleteInvoiceWhenGivenCorrectId(){

        User mockUser = new User(1,"test1", "password", "test1@me.com", "test", "one", UserRole.USER);
        Invoice mockInvoice = new Invoice(1,mockUser,100F,"1/1/2020 1:00:00");

        when(invoiceRepository.deleteById(1)).thenReturn(true);
        when(invoiceRepository.findById(1)).thenReturn(mockInvoice);

        boolean result = invoiceService.deleteInvoiceById(1);

        assertTrue(result);

    }

    @Test
    public void shouldThrowBadRequestException(){

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {

            invoiceService.deleteInvoiceById(0);

        });

    }

    @Test
    public void shouldThrowResourceNotFoundException(){

        when(invoiceRepository.findById(100)).thenReturn(null);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {

           invoiceService.deleteInvoiceById(100);

        });

    }

}
