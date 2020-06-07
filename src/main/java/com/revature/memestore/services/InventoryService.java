package com.revature.memestore.services;

import com.revature.memestore.exceptions.BadRequestException;
import com.revature.memestore.exceptions.ResourceNotFoundException;
import com.revature.memestore.exceptions.ResourcePersistenceException;
import com.revature.memestore.models.Inventory;
import com.revature.memestore.repos.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;


    @Autowired
    public InventoryService(InventoryRepository repo){
        super();
        this.inventoryRepository = repo;
    }

    @Transactional(readOnly = true)
    public List<Inventory> getAllItems() {
        List<Inventory> response = inventoryRepository.getAll();

        if(response.isEmpty()){
            throw new ResourceNotFoundException("No Inventory inside database");
        }
        return response;
    }

    @Transactional(readOnly = true)
    public Inventory getInvoiceById(int id) {
        if(id < 1 || Float.isNaN(id)){
            throw new BadRequestException("Invalid Id was provided");
        }
        Inventory item = inventoryRepository.findById(id);

        if(item == null){
            throw new ResourceNotFoundException("No item found with provided Id");
        }
        return item;
    }

    @Transactional(readOnly = true)
    public Inventory getInvoiceByName(String itemName){
        if(itemName == null || itemName.trim().equals("")){
            throw new BadRequestException("Invalid item name provided");
        }

        Inventory item = inventoryRepository.findByItemName(itemName);

        if(item == null){
            throw new ResourceNotFoundException("No item found with provided item name");
        }
        return item;
    }

    @Transactional
    public Inventory saveNewInventory(Inventory newObj) {
        if(newObj == null || newObj.getDetails().trim().equals("") || newObj.getCategory().trim().equals("")
            || newObj.getItem_image().trim().equals("") || newObj.getItem_name().trim().equals("")){
            throw new BadRequestException("Invalid Object Provided");
        }

        Inventory response = inventoryRepository.findByItemName(newObj.getItem_name());
        if(response != null){
            throw new ResourcePersistenceException("Item name is already in use");
        }

        return inventoryRepository.save(newObj);
    }

    @Transactional
    public boolean updateInventory(Inventory updatedInventory) {

        if(updatedInventory == null || updatedInventory.getDetails().trim().equals("") || updatedInventory.getCategory().trim().equals("")
                || updatedInventory.getItem_image().trim().equals("") || updatedInventory.getItem_name().trim().equals("")){
            throw new BadRequestException("Invalid Object Provided");
        }

        Inventory itemToUpdate = inventoryRepository.findById(updatedInventory.getItem_id());

        if(updatedInventory == null){
            throw new ResourceNotFoundException("No items to update with provided Id");
        }

        boolean response = isItemNameAvailable(updatedInventory);


        if(updatedInventory.getItem_name().equals(itemToUpdate.getItem_name())){
            return false;
        }

        if(response){
            throw new ResourcePersistenceException("Item name is already in use.");
        }

        return inventoryRepository.update(updatedInventory);
    }

    @Transactional
    public boolean deleteInventoryById(int id) {
        if(id < 1 || Float.isNaN(id)){
            throw new BadRequestException("Invalid Id was provided");
        }

        Inventory response = inventoryRepository.findById(id);
        if(response == null){
            throw new ResourceNotFoundException("No item found with provided Id");
        }

        return inventoryRepository.deleteById(id);
    }

    private boolean isItemNameAvailable(Inventory itemName){
        Inventory response = inventoryRepository.findByItemName(itemName.getItem_name());
        if(response != null){
            return true;
        }
        return false;
    }
}
