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
public class InventoryService implements CrudRepository<Inventory> {

    private InventoryRepository inventoryRepository;


    @Autowired
    public InventoryService(InventoryRepository repo){
        super();
        this.inventoryRepository = repo;
    }

    @Transactional(readOnly = true)
    public List<Inventory> getAll() {
        List<Inventory> inventory = inventoryRepository.getAll();
        if(inventory.size() == 0 ){
            throw new ResourceNotFoundException("There are no items in the Inventory");
        }
        return inventory;
    }

    @Transactional(readOnly = true)
    public Inventory findById(int id) {
        if(id < 1 || Float.isNaN(id)){
            throw new BadRequestException("Invalid Id was provided");
        }
        Inventory item = inventoryRepository.findById(id);

        if(item == null){
            throw new ResourceNotFoundException("No item found with provided Id");
        }
        return item;
    }

    @Transactional
    public Inventory save(Inventory newObj) {
        if(newObj == null || newObj.getDetails().equals("") || newObj.getCategory().equals("")
            || newObj.getItem_image().equals("") || newObj.getItem_name().equals("")){
            throw new BadRequestException("Invalid Object Provided");
        }


        return inventoryRepository.save(newObj);
    }

    @Override
    public boolean update(Inventory updatedObj) {

        return false;
    }

    @Transactional
    public boolean deleteById(int id) {
        if(id < 1 || Float.isNaN(id)){
            throw new BadRequestException("Invalid Id was provided");
        }
        inventoryRepository.deleteById(id);
        return true;
    }
}
