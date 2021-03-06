package com.revature.memestore.web.controllers;

import com.revature.memestore.services.UserService;
import com.revature.memestore.web.dtos.Credentials;
import com.revature.memestore.web.dtos.Principal;
import com.revature.memestore.web.security.JwtConfig;
import com.revature.memestore.web.security.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService service){

        this.userService = service;

    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Principal authenticate(@RequestBody Credentials creds, HttpServletResponse resp){

        Principal payload = userService.authenticate(creds);
        resp.setHeader(JwtConfig.HEADER, TokenGenerator.createJwt(payload));

        return payload;

    }

}
