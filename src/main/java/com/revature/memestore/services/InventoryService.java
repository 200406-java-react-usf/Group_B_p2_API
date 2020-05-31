package com.revature.memestore.services;

import com.revature.memestore.exceptions.BadRequestException;
import com.revature.memestore.exceptions.ResourceNotFoundException;
import com.revature.memestore.models.Inventory;
import com.revature.memestore.repos.CrudRepository;
import com.revature.memestore.repos.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.Float.NaN;

@Service
public class InventoryService {

    private InventoryRepository inventoryRepository;


    @Autowired
    public InventoryService(InventoryRepository repo){
        super();
        this.inventoryRepository = repo;
    }

    @Transactional(readOnly = true)
    public List<Inventory> getAllItems() {
        //
        return inventoryRepository.getAll();
    }

    @Transactional(readOnly = true)
    public Inventory getInvoiceById(int id) {
//        if(id < 1 || Float.isNaN(id)){
//            throw new BadRequestException("Invalid Id was provided");
//        }
        Inventory item = inventoryRepository.findById(id);

//        if(item == null){
//            throw new ResourceNotFoundException("No item found with provided Id");
//        }
        return item;
    }

    @Transactional
    public Inventory saveNewInventory(Inventory newObj) {
//        if(newObj == null || newObj.getDetails().equals("") || newObj.getCategory().equals("")
//            || newObj.getItem_image().equals("") || newObj.getItem_name().equals("")){
//            throw new BadRequestException("Invalid Object Provided");
//        }

        return inventoryRepository.save(newObj);
    }

    @Transactional
    public boolean updateInventory(Inventory updatedInventory) {

        //Validation

        return inventoryRepository.update(updatedInventory);
    }

    @Transactional
    public boolean deleteInventoryById(int id) {
//        if(id < 1 || Float.isNaN(id)){
//            throw new BadRequestException("Invalid Id was provided");
//        }
        ;
        return inventoryRepository.deleteById(id);
    }
}
