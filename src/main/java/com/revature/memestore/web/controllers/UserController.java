package com.revature.memestore.web.controllers;

import com.revature.memestore.models.Invoice;
import com.revature.memestore.models.User;
import com.revature.memestore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){

        this.userService = userService;

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers(HttpServletRequest req){

        return userService.getAllUsers();

    }

    @GetMapping(value = "/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@RequestParam(required = false) int value){

        return userService.getUserById(value);

    }

    @GetMapping(value = "/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Invoice> getUserInvoicesById(@RequestParam int id){

        return userService.getUsersInvoicesById(id);

    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User registerNewUser(@RequestBody User newUser){

        return userService.register(newUser);

    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateUser(@RequestBody User updatedUser){

        return userService.updateUser(updatedUser);

    }

    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteUserById(@RequestParam int id){

        return userService.deleteUserById(id);

    }

}
