package com.revature.memestore.web.controllers;

import com.revature.memestore.models.User;
import com.revature.memestore.services.UserService;
import com.revature.memestore.web.dtos.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService service){

        this.userService = service;

    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User authenticate(@RequestBody Credentials creds){

        return userService.authenticate(creds);

    }

}
