package com.revature.memestore.web.controllers;


import com.revature.memestore.models.Inventory;
import com.revature.memestore.services.InventoryService;
import com.revature.memestore.web.security.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;




import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Inventory> getAllItems(HttpServletRequest req){

        return inventoryService.getAllItems();
    }

    @GetMapping(value = "/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public Inventory getInvoiceBeId(@RequestParam(required = false)int value){
        return inventoryService.getInvoiceById(value);
    }

    @GetMapping(value = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
    public Inventory getInvoiceByName(@RequestParam(required = false)String value){
        return inventoryService.getInvoiceByName(value);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Inventory saveNewInventory(@RequestBody Inventory newItem){
        return inventoryService.saveNewInventory(newItem);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateInventory(@RequestBody Inventory updatedItem){
        return inventoryService.updateInventory(updatedItem);
    }

    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteInventoryById(@RequestParam int id){

        return inventoryService.deleteInventoryById(id);
    }


}



