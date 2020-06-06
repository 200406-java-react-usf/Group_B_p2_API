package com.revature.memestore.services;

import com.revature.memestore.exceptions.ResourceNotFoundException;
import com.revature.memestore.models.Inventory;
import com.revature.memestore.repos.InventoryRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @Mock
    private static InventoryRepository mockRepo;

    @InjectMocks
    private static InventoryService inventoryService;

//    private static List<Inventory> mockInventory;
//
//    @BeforeClass
//    public static void setup(){
//        mockRepo = mock(InventoryRepository.class);
//        mockInventory = new ArrayList<>();
//        Inventory a = new Inventory(1,"test1", "details test 1", 100.00F, "random category 1", "random image 1" );
//        Inventory b = new Inventory(2,"test2", "details test 2", 100.00F, "random category 2", "random image 2" );
//        Inventory c = new Inventory(3,"test3", "details test 3", 100.00F, "random category 3", "random image 3" );
//        Inventory d = new Inventory(4,"test4", "details test 4", 100.00F, "random category 4", "random image 4" );
//        Inventory e = new Inventory(5,"test5", "details test 5", 100.00F, "random category 5", "random image 5" );
//        mockInventory.add(a);
//        mockInventory.add(b);
//        mockInventory.add(c);
//        mockInventory.add(d);
//        mockInventory.add(e);
//
//    }

    @Test
    public void getAllItems1(){
        List<Inventory> datas =  new ArrayList<>(_
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getAllItemsThrowResourceNotFoundException(){
        given(mockRepo.getAll()).willReturn(null);
        System.out.println(mockRepo.getAll());
        mockRepo.getAll();

    }




}
