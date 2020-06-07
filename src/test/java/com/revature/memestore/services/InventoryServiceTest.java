package com.revature.memestore.services;

import com.revature.memestore.exceptions.BadRequestException;
import com.revature.memestore.exceptions.ResourceNotFoundException;
import com.revature.memestore.exceptions.ResourcePersistenceException;
import com.revature.memestore.models.Inventory;
import com.revature.memestore.repos.InventoryRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.criteria.CriteriaBuilder;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getAllItems1(){
        List<Inventory> mockInventory = new ArrayList<>();
        mockInventory.add(new Inventory(1,"test1", "details test 1", 100.00F, "random category 1", "random image 1" ));
        mockInventory.add(new Inventory(2,"test2", "details test 2", 100.00F, "random category 2", "random image 2" ));
        mockInventory.add(new Inventory(3,"test3", "details test 3", 100.00F, "random category 3", "random image 3" ));
        mockInventory.add(new Inventory(4,"test4", "details test 4", 100.00F, "random category 4", "random image 4" ));
        mockInventory.add(new Inventory(5,"test5", "details test 5", 100.00F, "random category 5", "random image 5" ));
        given(inventoryRepository.getAll()).willReturn(mockInventory);

        List<Inventory> expected = inventoryService.getAllItems();

        assertEquals(expected, mockInventory);

    }

    @Test
    public void getAllItemsResourceNotFoundException(){

        List<Inventory> mockInventory = new ArrayList<Inventory>();

        when(inventoryRepository.getAll()).thenReturn(mockInventory);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, ()->{
            inventoryService.getAllItems();
        });
    }

    @Test
    public void findItemById(){
        final int id = 1;

        List<Inventory> mockInventory = new ArrayList<>();
        mockInventory.add(new Inventory(1,"test1", "details test 1", 100.00F, "random category 1", "random image 1" ));
        mockInventory.add(new Inventory(2,"test2", "details test 2", 100.00F, "random category 2", "random image 2" ));
        mockInventory.add(new Inventory(3,"test3", "details test 3", 100.00F, "random category 3", "random image 3" ));
        mockInventory.add(new Inventory(4,"test4", "details test 4", 100.00F, "random category 4", "random image 4" ));
        mockInventory.add(new Inventory(5,"test5", "details test 5", 100.00F, "random category 5", "random image 5" ));
        given(inventoryRepository.findById(id)).willReturn(mockInventory.get(id));
        Inventory expected = inventoryService.getInvoiceById(id);

        assertEquals(expected,mockInventory.get(id));

    }

    @Test
    public void getInvoiceByIdBadRequestException(){
        final int itemId = 0;
        List<Inventory> mockInventory = new ArrayList<>();
        mockInventory.add(new Inventory(1,"test1", "details test 1", 100.00F, "random category 1", "random image 1" ));
        mockInventory.add(new Inventory(2,"test2", "details test 2", 100.00F, "random category 2", "random image 2" ));
        mockInventory.add(new Inventory(3,"test3", "details test 3", 100.00F, "random category 3", "random image 3" ));
        mockInventory.add(new Inventory(4,"test4", "details test 4", 100.00F, "random category 4", "random image 4" ));
        mockInventory.add(new Inventory(5,"test5", "details test 5", 100.00F, "random category 5", "random image 5" ));
        when(inventoryRepository.findById(itemId)).thenReturn(mockInventory.get(itemId));

        BadRequestException thrown = assertThrows(BadRequestException.class, ()->{
            inventoryService.getInvoiceById(itemId);
        });
    }

    @Test
    public void findByItemName1(){
        Inventory a = new Inventory(1,"test1", "details test 1", 100.00F, "random category 1", "random image 1" );
        final String id = "test1";
        given(inventoryRepository.findByItemName(id)).willReturn(a);

        Inventory expected = inventoryService.getInvoiceByName(id);

        assertEquals(expected,a);
    }

    @Test
    public void findByItemNameBadRequestException(){
        final String itemName = "";
        Inventory a = new Inventory(1,"test1", "details test 1", 100.00F, "random category 1", "random image 1" );

        given(inventoryRepository.findByItemName(itemName)).willReturn(a);

        BadRequestException thrown = assertThrows(BadRequestException.class, ()->{
            inventoryService.getInvoiceByName(itemName);
        });
    }

    @Test
    public void findByItemNameResourceNotFoundException(){
        final String itemName = "Item Image";
        Inventory a = null;
        given(inventoryRepository.findByItemName(itemName)).willReturn(a);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, ()->{
            inventoryService.getInvoiceByName(itemName);
        });
    }

    @Test
    public void saveNewItemTest(){
        Inventory a = new Inventory(1,"test1", "details test 1", 100.00F, "random category 1", "random image 1" );

        given(inventoryRepository.save(a)).willReturn(a);

        Inventory expected = inventoryService.saveNewInventory(a);

        assertEquals(expected,a);

    }

    @Test
    public void saveNewItemBadRequestExceptionTest(){
        Inventory a = null;

        given(inventoryRepository.save(a)).willReturn(a);

        BadRequestException thrown = assertThrows(BadRequestException.class, ()->{
            inventoryService.saveNewInventory(a);
        });
    }

    @Test
    public void saveNewItemResourcePersistenceExceptionTest(){
        Inventory mockInventory1 = new Inventory(1,"test1", "details test 1", 100.00F, "random category 1", "random image 1" );
        Inventory mockInventory2 = new Inventory(2,"test1", "details test 1", 100.00F, "random category 1", "random image 1" );

        when(inventoryRepository.findByItemName(mockInventory1.getItem_name())).thenReturn(mockInventory2);
        System.out.println(inventoryRepository.findByItemName(mockInventory1.getItem_name()));
        ResourcePersistenceException thrown = assertThrows(ResourcePersistenceException.class,()->{
            inventoryService.saveNewInventory(mockInventory1);
        });
    }

    @Test
    public void updateInventoryTest(){
        Inventory mockInventoryPersisted = new Inventory(1,"test1", "details test 1", 100.00F, "random category 1", "random image 1" );
        Inventory mockInventoryUpdate = new Inventory(1,"update", "details update 1", 100.00F, "random update 1", "random image update 1" );

        when(inventoryRepository.update(mockInventoryUpdate)).thenReturn(true);

        when(inventoryRepository.findByItemName(mockInventoryUpdate.getItem_name())).thenReturn(null);
        System.out.println(inventoryRepository.findByItemName(mockInventoryUpdate.getItem_name()));

        when(inventoryRepository.findById(mockInventoryUpdate.getItem_id())).thenReturn(mockInventoryPersisted);
        System.out.println(inventoryRepository.findById(mockInventoryUpdate.getItem_id()));

        boolean result = inventoryService.updateInventory(mockInventoryUpdate);
        System.out.println(result);

        assertTrue(result);
    }

    @Test
    public void shouldReturnTrueWhenItemSuccessfullyDeletes(){
        Inventory mockInventory = new Inventory(1,"test1", "details test 1", 100.00F, "random category 1", "random image 1" );

        when(inventoryRepository.findById(1)).thenReturn(mockInventory);
        when(inventoryRepository.deleteById(1)).thenReturn(true);

        assertTrue(inventoryService.deleteInventoryById(1));
    }

}
